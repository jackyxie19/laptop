package utils

import java.text.SimpleDateFormat
import java.util.Date

/**
  * Created by wandepeng on 2017/7/11.
  */
object DateUtils {
  /**
    * 获取当前日期,模拟传入数据的时间字段
    *
    * @return 每条数据时间字段
    */
  def getCurrentDate(): String = {
    val now: Date = new Date()
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyyMMdd")
    val currentDate = dateFormat.format(now)
    currentDate
  }

  def getCurrentDateX(): String = {
    val now: Date = new Date()
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
    val currentDate = dateFormat.format(now)
    currentDate
  }
}
