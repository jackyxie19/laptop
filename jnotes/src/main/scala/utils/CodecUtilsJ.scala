package utils

import java.io.File

import org.apache.commons.io.FileUtils

object CodecUtilsJ {
  def main(args: Array[String]): Unit = {
    val filename=""
    val file = new File(filename)
    val content = FileUtils.readFileToString(file, "gbk")
    FileUtils.write(file, content, "UTF-8")
  }
}
