package jnotes

/**
  * Created by jacky on 2017/7/12.
  */
object TraitImplementsTest {
  def main(args: Array[String]): Unit = {
    println(One.brokers)
  }
}

trait PublicVariable{
  val brokers = "kafka_broker"
  val kafkaPartitions = 8
}

object One extends PublicVariable{
  val topic = "topic"
}