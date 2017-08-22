import com.typesafe.config.{Config, ConfigFactory}
import org.junit.{Before, Test}

class ScalaTest {
  var conf:Config = _

  @Before
  def init(): Unit ={
    conf = ConfigFactory.load()
  }

  @Test
  def testSplit(): Unit ={

    val sep = conf.getString("APP_ONE.FILTER_NUM_SEPARATOR")
    println(sep)
    val arr = "TS9527|TS9528,aaa".split(sep)
    arr.foreach(println)
  }
}
