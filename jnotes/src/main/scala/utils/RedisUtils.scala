package utils

import org.apache.commons.lang3.StringUtils

import scala.collection.JavaConversions.bufferAsJavaList
import scala.collection.JavaConversions.seqAsJavaList
import scala.collection.mutable.ArrayBuffer
/**
  * Created by jacky on 2017/7/20.
  */
object RedisUtils {
  def main(args: Array[String]): Unit = {
    val arr = ArrayBuffer("hello","world","tom","jerry")
//    val str = StringUtils.join(arr.toArray,',')
//    println(str)
//    val str2 = StringUtils.join(arr,'|')
//    println(str2)
//    val str1 = JavaUtils.listToString5(arr,',')
//    println(str1)
//    val str3 = getRedisKey('|',"tom","cat","hello","world")
//    println(s"-----------$str3")



//    test


  }

  def getRedisKey(separator:Char,args: String*) ={
     JavaUtils.listToString5(args,separator)
  }

  def getPatternHash(separator:Char,patterNum:String,currentDate:String) ={
    getRedisKey(separator,patterNum,currentDate)
  }
  def getPatternHashKey(separator:Char,patterNum:String,identity:String)={
    getRedisKey(separator,patterNum,identity)
  }

  def listRedisKeys(separator:Char, patternNum:String,criterionSign:String, currentDate:String, identity:String, illegalSign:String, detailSign:String)={
    val pattHash = getRedisKey(separator,patternNum,criterionSign,currentDate)
    val patternHashKey = getRedisKey(separator,patternNum,identity)
    val illegalHash = getRedisKey(separator,patternNum,illegalSign,currentDate)
    val detailList = getRedisKey(separator,patternNum,detailSign,currentDate)
    (pattHash,patternHashKey,illegalHash,detailList)
  }

  def listRedisKeys()()={

  }

  def listRedisName()={

  }

  def test: Unit ={
    val pattern = "cz222"
    val problem = "illegal"
    val record = "details"
    val criterion = "criterion"
    val rs = '-'
    val modelKey = "modelKey"
    val date = DateUtils.getCurrentDate
    val pattHash = getRedisKey(rs,pattern,criterion,date)
    val patternHashKey = getRedisKey(rs,pattern,modelKey)
    val problemHash = getRedisKey(rs,pattern,problem,date)
    val recordList = getRedisKey(rs,pattern,record,date)
    println(s"$pattHash\r\n$patternHashKey\r\n$problemHash\r\n$recordList")
  }
}
