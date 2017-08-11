package utils

import java.util

import net.sf.json.JSONObject

import scala.collection.JavaConverters._

object String1Utils {
  def main(args: Array[String]): Unit = {
    val aa = "he|ha|en|na|eq"
    val bb = for(i<- 0 to 10) yield aa+i
    val json = new JSONObject()
    val cc = bb.map(x=>{
      val array = x.split('|')
      array(3)
    })
    println(cc)
    import scala.collection.JavaConversions._
    import java.{util => ju}
    val dd :ju.List[String] = cc
    val ee : Seq[String] = dd
//    for(i<-0 until dd.size()) println(dd(i))
    for(i<-0 until ee.size()) println(ee(i))

    json.put("test",dd)
    println(json)
//    import scala.collection.JavaConversions.seqAsJavaList
//    val strings = getSplit2(bb,3,'|')
//    println(strings)
//    val str = getSplit(aa,3,'|')
//    println(str)
//    println(bb)
//    val unit = str2field(bb)(getSplit(_,3,'|'))
//    val strings1 = str2field(bb)(x=>x+2)
//    println(unit)
//    println(strings1)
//    val strings2 = str2field(bb)(x=>getSplit(x,3,'|'))
//    println(strings2)
  }
  def getSplit(str:String,index:Int,spt:Char)={
    val split = str.split(spt)
    split(index)
  }
  def getSplit2(list:java.util.List[String],index:Int,spt:Char)()={
    val values = new util.ArrayList[String]()

    values
  }
  def str2field(seq:Seq[String])(func:String=>String)={
    val seq1 = seq.map(func)
    seq1
  }
}
