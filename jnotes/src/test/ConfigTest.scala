import com.typesafe.config.ConfigFactory
import org.junit.Test

class ConfigTest {
  @Test
  def testLoad(): Unit ={
    val conf = ConfigFactory.load()
    println(conf.getStringList("TEST_LIST.a"))
  }
  @Test
  def testMark(): Unit ={
    val conf = ConfigFactory.load()
    println(conf.getStringList("TEST_LIST.b"))
  }
}
