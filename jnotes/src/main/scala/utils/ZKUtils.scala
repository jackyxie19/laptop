package utils

import com.typesafe.config.ConfigFactory
import org.apache.zookeeper.ZooDefs.Ids
import org.apache.zookeeper.{CreateMode, WatchedEvent, Watcher, ZooKeeper}

object ZKUtils {
  val conf = ConfigFactory.load()
  val watcher = new Watcher {
    override def process(event: WatchedEvent): Unit = println(s"haha"+event.getPath)
  }
  val zk = new ZooKeeper(conf.getString("ZOOKEEPER.HOST_AND_PORT"),3000,watcher)
  def main(args: Array[String]): Unit = {

    val zk = new ZooKeeper(conf.getString("ZOOKEEPER.HOST_AND_PORT"),3000,watcher)
    zk.create("/abc","myData2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
    zk.close()
  }
}
