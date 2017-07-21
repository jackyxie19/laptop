package log4j2test


import org.apache.logging.log4j.LogManager

import scala.io.Source

/**
  * Created by jacky on 2017/7/19.
  */
object Log4j2 {
  def main(args: Array[String]): Unit = {
//    demo1
    val pair = (("a","b"),("c"))
    println(pair)
  }

  private def demo3={
    for(i<- 0 to 5000){
      demo2
    }
    while(true){
      demo2
      Thread.sleep(1000)
    }
  }

  /**
    * 从指定位置读取配置文件,not classpath
    * @return
    */
  private def demo4 ={
    import org.apache.logging.log4j.LogManager
    import org.apache.logging.log4j.core.config.ConfigurationSource
    import org.apache.logging.log4j.core.config.Configurator
    import java.io.BufferedInputStream
    import java.io.FileInputStream
    import java.io.File
    val file = new File("F:/logs/log4j2.xml")
    val in = new BufferedInputStream(new FileInputStream(file))
    val source = new ConfigurationSource(in)
    Configurator.initialize(null, source)
    val logger = LogManager.getLogger("mylog")
  }

  private def demo2 = {
    val logger = LogManager.getLogger("mylog")
    logger.trace("trace level")
    logger.debug("debug level")
    logger.info("info level")
    logger.warn("warn level")
    logger.error("error level")
    logger.fatal("fatal level")
  }

  private def demo1 = {
    val logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME)
    logger.trace("trace level")
    logger.debug("debug level")
    logger.info("info level")
    logger.warn("warn level")
    logger.error("error level")
    logger.fatal("fatal level")
  }

}
