package utils

import net.sf.json.JSONObject

object MyJSONUtils {
  def main(args: Array[String]): Unit = {
    val json = new JSONObject
    val set = new java.util.HashSet[String]()
    set.add("123")
    set.add("1234")
    set.add("12345")
    set.add("123456")
    val str = JavaUtils.setToString5(set,'|')
    println(str)
    println(set)
    json.put("aa",set)
    println(json)
    val tjson = JSONObject.fromObject("{\"aa\":[\"123\",\"1234\",\"123456\",\"12345\"]}")
    val unit = tjson.get("aa")
    println(unit)
//    val set1 = unit.asInstanceOf[java.util.Set[String]]
    val set1 = unit.asInstanceOf[net.sf.json.JSONArray]
    val i = set1.size()
    println(s"size$i")
    val array = set1.toArray
    array.foreach(println)
  }
}
