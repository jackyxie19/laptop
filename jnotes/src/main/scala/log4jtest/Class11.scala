package log4jtest

import org.apache.log4j.Logger

/**
  * Created by jacky on 2017/7/19.
  */
object Class11 {
  def main(args: Array[String]): Unit = {
    val logger = Logger.getLogger(Class11.getClass)
    println(Class11.getClass.getName)
//    val logger = Logger.getLogger("log4jtest.Class11")
    logger.trace("trace level")
    logger.debug("debug level")
    logger.info("info level")
    logger.warn("warn level")
    logger.error("error level")
    logger.fatal("fatal level")
  }
}
