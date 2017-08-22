package sparkstreaming

import com.typesafe.config.{Config, ConfigFactory}
import net.sf.json.JSONObject
import org.apache.log4j.{LogManager, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies._
import org.apache.spark.streaming.kafka010.LocationStrategies._
import org.apache.spark.streaming.kafka010.{HasOffsetRanges, KafkaUtils}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import redis.JedisClusterClient
import redis.clients.jedis.JedisCluster
import utils._


/**
  * Created by duyanfeng on 2017/5/15.
  */
object Demo {

  val redisConn: JedisCluster = JedisClusterClient.getJedisCluster.getJedisConn
  val conf: Config = ConfigFactory.load()
  val LOGGER: Logger = LogManager.getLogger(conf.getString("APP_ONE.LOGGER"))

  /**
    * Json为空过滤,过滤出EXCEPTION数据
    * @param json 一条json数据.
    * @return
    */
  def appOneFilter(json: JSONObject): Boolean ={
    val value = json.get(conf.getString("APP_ONE.FIELD_NAME"))
    if(value==null) return false
    if(value.equals(conf.getString("JSON.NULL_VALUE"))) return false
    if(value.equals(conf.getString("APP_ONE.FIELD_NAME_EXCEPTION")))  true else  false
  }

  def persist(json:JSONObject): Unit ={
    println("persisted"+json)
  }

  def serviceAppOne(json:JSONObject,conn:JedisCluster): Unit ={
    val rs = conf.getString("REDIS.KEY_SEPARATOR") //redis separator
    val maxSleepingTimes = conf.getInt("APP_ONE.MAX_SLEEPING")
    var satisfied = false
    var needPersisted = false
    val patternNum = conf.getString("APP_ONE.PATTERN_NUM")
    val date = DateUtils.getCurrentDate()
    val name = json.optString(conf.getString("JSON.FIELD_NAME"),conf.getString("JSON.NULL_VALUE"))
    val criterionSign = conf.getString("APP_ONE.CRITERION_SIGN")
    val illegalSign = conf.getString("APP_ONE.ILLEGAL_SIGN")
    val criterion = RedisKeyUtils.fieldsJoin(rs,patternNum,criterionSign,date,name)
    val illegal = RedisKeyUtils.fieldsJoin(rs,patternNum,illegalSign,date,name)
    val increment = conf.getLong("APP_ONE.INCREMENT")

    conn.hincrBy(criterion,name,increment)
    val sleepingTimes = conn.hget(criterion,name)

    if (sleepingTimes==null){}
    else{
      if (sleepingTimes.toInt>=maxSleepingTimes){
        satisfied = true
      }
    }

    if(satisfied) {
      val str = conn.get(illegal) + ""
      if (str.equals("")) {
        needPersisted=true
      }
      if(needPersisted){
        persist(json)
      }
    }
  }

  def main(args: Array[String]) {
    val conf = ConfigFactory.load()
    val (brokers, streamingInterval, topicPartitionNum) = InitUtils.getPubConf()
    val topic = conf.getString("KAFKA_TOPIC.MONDAY")
    val offsetPre = conf.getString("APP_ONE.OFFSET_PRE")
    val appName = conf.getString("APP_ONE.APP_ONE_NAME")
    val groupId = conf.getString("KAFKA_GROUP.JANUARY")

//    val sparkConf = new SparkConf().setAppName(appName) //.setMaster("spark://197.1.25.235:7077")
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName(appName)
    val ssc = new StreamingContext(sparkConf, Seconds(streamingInterval))

    val kafkaParams = InitUtils.getKafkaParam(brokers, groupId)

    // 存储从redis获取的kafka偏移量
    val fromOffsets = OffsetUtils.getOffsetsFromRedis(topicPartitionNum, offsetPre, topic)

    val kafkaDStream = KafkaUtils.createDirectStream[String, String](
      ssc, PreferConsistent,
      Assign[String, String](fromOffsets.keys.toList, kafkaParams, fromOffsets)
    )

    //    val dStream = ssc.socketTextStream("197.3.196.33", 6666)
    //    val dStream: DStream[String] = kafkaDStream.map(_.value())
    //    val dStream: DStream[String] = kafkaDStream.map(_.value())

    /*
    * 记录kafka全量消息,日志存在Executor节点
    * */
    val dStream: DStream[String] = kafkaDStream.map(line => {
      val partition = line.partition()
      val value = line.value()
      val topic = line.topic()
      val offset = line.offset()
      LOGGER.info(s"$topic:$partition:$offset:$value")
      value
    })

    /*
    * 格式过滤
    * */
    val sourceData = dStream.map(FilterUtils.jsonFunc).filter(FilterUtils.isSome).map(_.get)

    /*
    * topic中不需要数据过滤
    * */
    val exceptionData = sourceData.filter(appOneFilter)

    /*
    * 基础过滤完成,业务逻辑开始
    * */
    exceptionData.foreachRDD { rdd =>
      rdd.foreachPartition { partition =>
        val conn = JedisClusterClient.getJedisCluster.getJedisConn
        partition.foreach (json=>serviceAppOne(json,conn))
      }
    }

    // 保存offset,供下次调用创建流.Redis和ZK同时保存偏移量
    kafkaDStream.foreachRDD { kafka_stream =>
      val offsetRanges = kafka_stream.asInstanceOf[HasOffsetRanges].offsetRanges
      OffsetUtils.storeOffsetsIn2Redis(offsetRanges, offsetPre)
      OffsetUtils.persistOffsetsZK(offsetRanges, groupId, offsetPre)
    }

    ssc.start()
    ssc.awaitTermination()
  }
}