package utils

/**
  * Created by wandepeng on 2017/7/20.
  */
object RedisKeyUtils {
  /**
    * 根据关键字和分隔符拼接成redis中的key
 *
    * @param separator 分隔符
    * @param args 关键字
    * @return
    */
  def fieldsJoin(separator: String, args:String*) ={
    import scala.collection.JavaConversions.seqAsJavaList
    JavaUtils.list2String(args,separator)
  }

  /**
    * 获取需要使用的redis数据结构名称
 *
    * @param separator 分隔符
    * @param patternNum 模型编号
    * @param criterionSign 判定集合标记
    * @param currentDate 当前日期
    * @param identity 当期模型的唯一标示
    * @param illegalSign 非法集合标记
    * @param detailSign 详细集合标记
    * @return 集合名称
    */
  def listRedisSeqName(separator: String, patternNum:String, currentDate:String, identity:String, criterionSign:String="Criterion", illegalSign:String="Illegal", detailSign:String="Detail")={
    val criterionHash = fieldsJoin(separator,patternNum,criterionSign,currentDate)
    val criterionSet = fieldsJoin(separator,patternNum,criterionSign,currentDate,identity)
    val illegalHash = fieldsJoin(separator,patternNum,illegalSign,currentDate)
    val detailList = fieldsJoin(separator,patternNum,detailSign,currentDate,identity)
    (criterionHash,illegalHash,detailList,criterionSet)
  }

  def main(args: Array[String]) {
    val tuple = (("a","ab"),("c","cd"))

  }
}
