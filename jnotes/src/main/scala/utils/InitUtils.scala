package utils

import com.typesafe.config.ConfigFactory
import org.apache.kafka.common.serialization.StringDeserializer

/**
  * Created by wandepeng on 2017/7/11.
  */
object InitUtils {
  /**
    * 从配置文件中读取Kafka参数
 *
    * @param kafkaBrokers 传入配置文件中Kafka.brokers
    * @param groupId GROUPID
    * @return 用于建立kafkaDirectStream的Map[String,Object]
    */
  def getKafkaParam(kafkaBrokers:String,groupId:String="example")={
    val kafkaParams = Map[String, Object]("bootstrap.servers" -> kafkaBrokers,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> groupId,
      "enable.auto.commit" -> "false",
      "session.timeout.ms" -> "60000",
      "heartbeat.interval.ms" -> "30000",
      "request.timeout.ms" -> "80000")
    kafkaParams
  }

  /**
    * 获取公有不变的配置,传入的是conf文件中的KEY
 *
    * @param broker Kafka集群URL
    * @param interval streaming时间间隔
    * @param partitionNum Kafka分区数量
    * @return
    */
  def getPubConf(broker:String="KAFKA_PUBLIC.BROKERS", interval:String="SPARK_STREAMING.INTERVAL_SECONDS", partitionNum:String="KAFKA_PUBLIC.PARTITION_NUM") ={
    val conf = ConfigFactory.load()
    val kafkaBroker = conf.getString(broker)
    val streamingInterval = conf.getInt(interval)
    val topicPartitionNum = conf.getInt(partitionNum)
    (kafkaBroker,streamingInterval,topicPartitionNum)
  }

}
