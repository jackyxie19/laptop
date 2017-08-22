package utils

import com.typesafe.config.{Config, ConfigFactory}
import kafka.utils.ZkUtils
import org.apache.kafka.common.TopicPartition
import org.apache.log4j.LogManager
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.OffsetRange
import redis.JedisClusterClient


object OffsetUtils {
  val conf: Config = ConfigFactory.load()
  val (zkUrl, sessionTimeout, connectionTimeout) = (conf.getString("ZOOKEEPER.HOST_AND_PORT"), 3000, 3000)
  val zkClientAndConnection = ZkUtils.createZkClientAndConnection(zkUrl, sessionTimeout, connectionTimeout)
  val zkUtils = new ZkUtils(zkClientAndConnection._1, zkClientAndConnection._2, false)
  val LOGGER = LogManager.getLogger("OffsetUtils")
  /**
    * 通过所需topic和groupId读取offsets
    * offset存储地址:/<prefix>/<groupId>/<topic>/<partition>
    * @param topics
    * @param groupId
    * @param startOffset kafka定期清理topic中内容,提供手动控制offset起始点功能
    * @return
    */
  @deprecated
  def readOffsetsFromZK(topics: String, groupId: String, prefix: String, startOffset: Long = 0L): Map[TopicPartition, Long] = {
    val topicPartOffsetMap = collection.mutable.HashMap.empty[TopicPartition, Long]
    val partitionMap = zkUtils.getPartitionsForTopics(Array(topics))
    partitionMap.foreach(topicPartitions => {
      //      val zKGroupTopicDirs = new ZKGroupTopicDirs(groupId,topicPartitions._1)
      topicPartitions._2.foreach(parition => {
        // /<prefix>/<groupId>/<topic>/<partition>
        val offsetPath = "/" + prefix + "/" + groupId + "/" + topics + "/" + parition
        try {
          val offsetStatTuple = zkUtils.readData(offsetPath)
          if (offsetStatTuple != null) {
            topicPartOffsetMap.put(new TopicPartition(topicPartitions._1, Integer.valueOf(parition)),
              offsetStatTuple._1.toLong)
//            println("zk", parition, offsetStatTuple._1.toLong)
          }
        } catch {
          case e: org.I0Itec.zkclient.exception.ZkNoNodeException =>
            //kafka定期清理topic中内容,提供手动控制offset起始点功能
            zkUtils.updatePersistentPath(offsetPath, startOffset.toString)
            topicPartOffsetMap.put(new TopicPartition(topicPartitions._1, Integer.valueOf(parition)), startOffset)
        }
      })
    })
    topicPartOffsetMap.toMap
  }

  /**
    * 存储offset到zk.
    *
    * @param offsets        kafka直连流获取.
    * @param groupId        kafka消费groupId用于生成偏移量path
    * @param storeEndOffset 是否以untilOffset存储,false则以fromOffset存储.
    */
  def persistOffsetsZK(offsets: Seq[OffsetRange], groupId: String, prefix: String, storeEndOffset: Boolean = false): Unit = {
    offsets.foreach(or => {
      //      val zKGroupTopicDirs = new ZKGroupTopicDirs(groupId,or.topic)
      val topic = or.topic
      val partition = or.partition
      val off = if (storeEndOffset) or.untilOffset else or.fromOffset
      val offsetPath = "/" + prefix + "/" + groupId + "/" + topic + "/" + partition
      //      println(offsetPath)
      val offsetVal = if (storeEndOffset) or.untilOffset else or.fromOffset
      zkUtils.updatePersistentPath(offsetPath, offsetVal.toString)
//      println(offsetPath, offsetVal)
//      LOGGER.debug(s"persisting offset details into redis - path:{$offsetPath}")
      if(storeEndOffset) LOGGER.warn("Persisting kafka offset use EndOffset.")
    })
  }

  /**
    * 从Redis中读取该模型各个topic分区的偏移量
    *
    * @param partitions kafka分区数
    * @param prefix     配置文件中此模型前缀
    * @param topic      kafkaTopic名称
    * @param startPoint 设置topic起始偏移量
    * @return 用于Assign方法的Map.包含topic每个分区和其偏移量
    */
  def getOffsetsFromRedis(partitions: Int, prefix: String, topic: String, groupId: String = "example", startPoint: Long = 0): Map[TopicPartition, Long] = {
    val fromOffsets = collection.mutable.Map[TopicPartition, Long]()
    val conn = JedisClusterClient.getJedisCluster.getJedisConn
    for (partition <- 0 until partitions) {
      //8个分区
      val offsetPath = "/" + prefix + "/" + groupId + "/" + topic + "/" + partition
      var off = conn.get(offsetPath)
      if (off == null || off.equals("null") || off.equals("0")) {
        try {
          val offsetStatTuple = zkUtils.readData(offsetPath)
          val zkOffset = offsetStatTuple._1
          if(zkOffset.toLong!=0L) {
            LOGGER.warn(s"redis has lost offset data, read from zookeeper. redis offset:{$off}, zookeeper offset:{$zkOffset},topic:{$topic}, partition:{$partition}, path:{$offsetPath}")
            off = zkOffset
          }
        } catch {
          case e: org.I0Itec.zkclient.exception.ZkNoNodeException => off = startPoint.toString
            LOGGER.warn(s"retrieving offset details - no previous records exists, start at input point(default 0):{$startPoint}, topic:{$topic}, partition:{$partition}, path:{$offsetPath}")
        }
      }
      println("re", partition, off)
      fromOffsets += (new TopicPartition(topic, partition) -> off.toLong)
    }
    fromOffsets.toMap
  }

  /**
    * 在redis中存储kafka topic partition的偏移量
 *
    * @param offsetRanges
    * @param prefix
    * @param storeEndOffset
    */
  def storeOffsetsIn2Redis(offsetRanges: Seq[OffsetRange], prefix: String, groupId: String = "example", storeEndOffset: Boolean = false): Unit = {
    offsetRanges.foreach { osr =>
      val conn = JedisClusterClient.getJedisCluster.getJedisConn
      val topic = osr.topic
      val partition = osr.partition
      val off = if (storeEndOffset) osr.untilOffset else osr.fromOffset
      val offsetPath = "/" + prefix + "/" + groupId + "/" + topic + "/" + partition
      conn.set(offsetPath, off.toString)
      // (doself_kafka:topic:partition,off)
      //      println(prefix + topic + ":" + part, off.toString)
//      println(offsetPath, off)
      LOGGER.debug(s"persisting offset path:{set $offsetPath $off}")
      if(storeEndOffset) LOGGER.warn("Persisting kafka offset use EndOffset.")
    }
  }

  def main(args: Array[String]) {
    val conf = ConfigFactory.load()
    val (brokers, streamingInterval, topicPartitionNum) = InitUtils.getPubConf()
    val topic = conf.getString("KAFKA_TOPIC.MONDAY")
    val offsetPre = conf.getString("APP_ONE.OFFSET_PRE")
    val appName = conf.getString("APP_ONE.APP_ONE_NAME")
    val groupId = conf.getString("KAFKA_GROUP.JANUARY")
    val rs = conf.getString("REDIS.KEY_SEPARATOR") //redis separator
    val patternNum = conf.getString("APP_ONE.PATTERN_NUM")

    //    val sparkConf = new SparkConf().setAppName(appName) //.setMaster("spark://197.1.25.235:7077")
//    val sparkConf = new SparkConf().setMaster("local[*]").setAppName(appName)
//    val ssc = new StreamingContext(sparkConf, Seconds(streamingInterval))

    val kafkaParams = InitUtils.getKafkaParam(brokers, groupId)
    val fromOffsets = getOffsetsFromRedis(topicPartitionNum, offsetPre, topic)
    fromOffsets.foreach(println)
  }

}
