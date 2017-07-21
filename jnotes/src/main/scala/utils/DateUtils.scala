package utils

import java.text.SimpleDateFormat
import java.util.Date

/**
  * Created by jacky on 2017/7/21.
  */
object DateUtils {
  def getCurrentDate = {
    val now = new Date()
    val dateFormate = new SimpleDateFormat("yyyyMMdd")
    dateFormate.format(now)
  }
}
