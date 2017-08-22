package utils

import com.typesafe.config.ConfigFactory
import net.sf.json.JSONObject
import org.apache.log4j.LogManager

/**
  * Created by wandepeng on 2017/7/11.
  */
object FilterUtils {
  /**
    * 将String转换成JSON对象
    * 对None的元素进行调用会抛出NoSuchElementException
    *
    * @param line streaming中每一条数据
    * @return 可以解析且对象不为空返回Some,不可解析返回None
    */
  def jsonFunc(line: String) = {
    try {
      val data = JSONObject.fromObject(line)
      data.isNullObject match {
        case false => Option(data)
        case true => {
          LOGGER.warn(s"JSONObject NullObject $line")
          None
        }
      }
    } catch {
      case e: Exception => {
        LOGGER.warn(s"JSONObject.fromObject() failed. Data : $line")
        None
      }
    }
  }

  /**
    * 偏函数,判断封装JSON对象的Option是否为空
    * Option类型Some为true,None为false
    */
  val isSome :PartialFunction[Option[JSONObject],Boolean] ={
    case None=>false
    case Some(_)=>true
  }

  val LOGGER = LogManager.getLogger("FilterUtils")

  def matchRules(json: JSONObject,field:String="strtsnum")={
    if(json.has(field)){
      json.get(field).toString
    }
  }


  def main(args: Array[String]) {
    val arr = Array("{\"customSerinum\":\"null\",\"strauthoperaternum\":\"0300555555\",\"strassembletransname\":\"null\",\"ivouchercount\":\"0\",\"iamount\":\"56000.00\",\"strchecksealauthoperaternum\":\"null\",\"irepairscanflag\":\"null\",\"dtendtime\":\"null\",\"chargeflag\":\"null\",\"dtstarttime2\":\"null\",\"strvouchernum\":\"null\",\"strtransactorcountry\":\"null\",\"strtransactorcertbegin\":\"null\",\"strFrontCheckFailedReason\":\"null\",\"stragentauthority\":\"null\",\"iupdatecountflag\":\"0\",\"strtransactorauthority\":\"null\",\"strcpcremark\":\"null\",\"iprintstatus\":\"null\",\"strexfield4\":\"null\",\"strotherbankname\":\"null\",\"strexfield5\":\"null\",\"strexfield2\":\"null\",\"strexfield3\":\"null\",\"strfinancecheckoperaternum\":\"null\",\"strreqappcode\":\"null\",\"strexfield1\":\"null\",\"iespecialflag\":\"null\",\"ihandprint\":\"null\",\"strreturncode\":\"null\",\"strtsname\":\"个人定期开户\",\"strsummary\":\"null\",\"dttimestamp\":\"Mon Jun 12 11:18:28 CST 2017\",\"cbstaskid\":\"null\",\"strcpcesname\":\"null\",\"dtstarttime\":\"Mon Jun 12 11:18:28 CST 2017\",\"straccounttype\":\"null\",\"strreportmsg\":\"null\",\"strSelfTelenum\":\"null\",\"strbpname\":\"null\",\"irectifyfrontstatus\":\"null\",\"strInspectJournal1\":\"null\",\"strtransactorcertend\":\"null\",\"strInspectJournal2\":\"null\",\"dtcpcendtime\":\"null\",\"strpath\":\"null\",\"strcpcflownum\":\"null\",\"strRetCheck1\":\"null\",\"strRetCheck2\":\"null\",\"stragentcountry\":\"null\",\"queryFlag\":\"null\",\"strremark\":\"null\",\"servicestatus\":\"null\",\"stragentcerttype\":\"null\",\"strtransactorphone\":\"null\",\"isealpurpose\":\"null\",\"stragentphone\":\"null\",\"strrecheckoperaternum\":\"null\",\"strvouchertype\":\"null\",\"strcurrencylist\":\"null\",\"strbpreqid\":\"030000000020170612111620989\",\"strcertificatetype\":\"null\",\"strSelfCertificateNum\":\"idno1500000088\",\"chargename\":\"null\",\"strtransactorname\":\"null\",\"iviaCertImgCheckFlag\":\"null\",\"mistakeoperator\":\"null\",\"stroperaternum\":\"0300000000\",\"strbranchauthsn\":\"null\",\"strscenarioname\":\"个人综合开户及签约\",\"strcpcesnum\":\"null\",\"irepairscanstatus\":\"null\",\"strsnlogid2\":\"null\",\"strtransactorcerttype\":\"null\",\"strcurrencycode\":\"RMB\",\"strSelfName\":\"测试1500000088\",\"istayinspectreason\":\"null\",\"ichecksealflag\":\"null\",\"dtcpcstarttime\":\"null\",\"dtcpcouttime\":\"null\",\"strFrontTaskFailedReason\":\"null\",\"stragentcertbegin\":\"null\",\"strotheraccountname\":\"null\",\"strrectifyreason\":\"null\",\"strtsnum\":\"TS0001000001\",\"stragentcertno\":\"null\",\"idumpstatus\":\"null\",\"iamount2\":\"null\",\"strSelfNationality\":\"null\",\"strfrontmessage\":\"null\",\"strRenhangRetCheck1\":\"null\",\"strRenhangRetCheck2\":\"null\",\"chargecode\":\"null\",\"iservicetype\":\"1\",\"strotheraccountnum\":\"null\",\"strscenarionum\":\"SC00010000\",\"chargeamount\":\"null\",\"stresname\":\"ES-EX-IntegrateAccountCreate-T\",\"strscenarionums\":\"null\",\"stresnum\":\"ES-EX-IntegrateAccountCreate-T\",\"customerFingerData\":\"null\",\"idebitflag\":\"null\",\"strcertificatenum\":\"null\",\"strbranchnum\":\"0300\",\"stragentcertend\":\"null\",\"iprintflag\":\"null\",\"id\":\"1434255\",\"strproductcode\":\"993010000000000000\",\"ijournaltype\":\"null\",\"stragentname\":\"null\",\"strbptype\":\"null\",\"faceImagelogid\":\"null\",\"ilockflag\":\"0\",\"straccountnum\":\"null\",\"strtransactorcertno\":\"null\",\"strtradeaction\":\"30100010000301001000001\",\"iselfCertImgCheckFlag\":\"null\",\"strotherbankno\":\"null\",\"strbpnum\":\"1500000088\",\"istatisticstype\":\"null\",\"iservicestatus\":\"16\",\"strsnlogid\":\"30101201706120300519489\",\"iproxyCertImgCheckFlag\":\"null\",\"strSelfCertificateType\":\"25\"}",
    "",
    "{\"customSerinum\":\"null\"\"strauthoperaternum\":\"0300555555\",\"strassembletransname\":\"null\",\"ivouchercount\":\"0\",\"iamount\":\"56000.00\",\"strchecksealauthoperaternum\":\"null\",\"irepairscanflag\":\"null\",\"dtendtime\":\"null\",\"chargeflag\":\"null\",\"dtstarttime2\":\"null\",\"strvouchernum\":\"null\",\"strtransactorcountry\":\"null\",\"strtransactorcertbegin\":\"null\",\"strFrontCheckFailedReason\":\"null\",\"stragentauthority\":\"null\",\"iupdatecountflag\":\"0\",\"strtransactorauthority\":\"null\",\"strcpcremark\":\"null\",\"iprintstatus\":\"null\",\"strexfield4\":\"null\",\"strotherbankname\":\"null\",\"strexfield5\":\"null\",\"strexfield2\":\"null\",\"strexfield3\":\"null\",\"strfinancecheckoperaternum\":\"null\",\"strreqappcode\":\"null\",\"strexfield1\":\"null\",\"iespecialflag\":\"null\",\"ihandprint\":\"null\",\"strreturncode\":\"null\",\"strtsname\":\"个人定期开户\",\"strsummary\":\"null\",\"dttimestamp\":\"Mon Jun 12 11:18:28 CST 2017\",\"cbstaskid\":\"null\",\"strcpcesname\":\"null\",\"dtstarttime\":\"Mon Jun 12 11:18:28 CST 2017\",\"straccounttype\":\"null\",\"strreportmsg\":\"null\",\"strSelfTelenum\":\"null\",\"strbpname\":\"null\",\"irectifyfrontstatus\":\"null\",\"strInspectJournal1\":\"null\",\"strtransactorcertend\":\"null\",\"strInspectJournal2\":\"null\",\"dtcpcendtime\":\"null\",\"strpath\":\"null\",\"strcpcflownum\":\"null\",\"strRetCheck1\":\"null\",\"strRetCheck2\":\"null\",\"stragentcountry\":\"null\",\"queryFlag\":\"null\",\"strremark\":\"null\",\"servicestatus\":\"null\",\"stragentcerttype\":\"null\",\"strtransactorphone\":\"null\",\"isealpurpose\":\"null\",\"stragentphone\":\"null\",\"strrecheckoperaternum\":\"null\",\"strvouchertype\":\"null\",\"strcurrencylist\":\"null\",\"strbpreqid\":\"030000000020170612111620989\",\"strcertificatetype\":\"null\",\"strSelfCertificateNum\":\"idno1500000088\",\"chargename\":\"null\",\"strtransactorname\":\"null\",\"iviaCertImgCheckFlag\":\"null\",\"mistakeoperator\":\"null\",\"stroperaternum\":\"0300000000\",\"strbranchauthsn\":\"null\",\"strscenarioname\":\"个人综合开户及签约\",\"strcpcesnum\":\"null\",\"irepairscanstatus\":\"null\",\"strsnlogid2\":\"null\",\"strtransactorcerttype\":\"null\",\"strcurrencycode\":\"RMB\",\"strSelfName\":\"测试1500000088\",\"istayinspectreason\":\"null\",\"ichecksealflag\":\"null\",\"dtcpcstarttime\":\"null\",\"dtcpcouttime\":\"null\",\"strFrontTaskFailedReason\":\"null\",\"stragentcertbegin\":\"null\",\"strotheraccountname\":\"null\",\"strrectifyreason\":\"null\",\"strtsnum\":\"TS0001000001\",\"stragentcertno\":\"null\",\"idumpstatus\":\"null\",\"iamount2\":\"null\",\"strSelfNationality\":\"null\",\"strfrontmessage\":\"null\",\"strRenhangRetCheck1\":\"null\",\"strRenhangRetCheck2\":\"null\",\"chargecode\":\"null\",\"iservicetype\":\"1\",\"strotheraccountnum\":\"null\",\"strscenarionum\":\"SC00010000\",\"chargeamount\":\"null\",\"stresname\":\"ES-EX-IntegrateAccountCreate-T\",\"strscenarionums\":\"null\",\"stresnum\":\"ES-EX-IntegrateAccountCreate-T\",\"customerFingerData\":\"null\",\"idebitflag\":\"null\",\"strcertificatenum\":\"null\",\"strbranchnum\":\"0300\",\"stragentcertend\":\"null\",\"iprintflag\":\"null\",\"id\":\"1434255\",\"strproductcode\":\"993010000000000000\",\"ijournaltype\":\"null\",\"stragentname\":\"null\",\"strbptype\":\"null\",\"faceImagelogid\":\"null\",\"ilockflag\":\"0\",\"straccountnum\":\"null\",\"strtransactorcertno\":\"null\",\"strtradeaction\":\"30100010000301001000001\",\"iselfCertImgCheckFlag\":\"null\",\"strotherbankno\":\"null\",\"strbpnum\":\"1500000088\",\"istatisticstype\":\"null\",\"iservicestatus\":\"16\",\"strsnlogid\":\"30101201706120300519489\",\"iproxyCertImgCheckFlag\":\"null\",\"strSelfCertificateType\":\"25\"}")
//    println(jsonFunc("{\"customSerinum\":\"null\",\"strauthoperaternum\":\"0300555555\",\"strassembletransname\":\"null\",\"ivouchercount\":\"0\",\"iamount\":\"56000.00\",\"strchecksealauthoperaternum\":\"null\",\"irepairscanflag\":\"null\",\"dtendtime\":\"null\",\"chargeflag\":\"null\",\"dtstarttime2\":\"null\",\"strvouchernum\":\"null\",\"strtransactorcountry\":\"null\",\"strtransactorcertbegin\":\"null\",\"strFrontCheckFailedReason\":\"null\",\"stragentauthority\":\"null\",\"iupdatecountflag\":\"0\",\"strtransactorauthority\":\"null\",\"strcpcremark\":\"null\",\"iprintstatus\":\"null\",\"strexfield4\":\"null\",\"strotherbankname\":\"null\",\"strexfield5\":\"null\",\"strexfield2\":\"null\",\"strexfield3\":\"null\",\"strfinancecheckoperaternum\":\"null\",\"strreqappcode\":\"null\",\"strexfield1\":\"null\",\"iespecialflag\":\"null\",\"ihandprint\":\"null\",\"strreturncode\":\"null\",\"strtsname\":\"个人定期开户\",\"strsummary\":\"null\",\"dttimestamp\":\"Mon Jun 12 11:18:28 CST 2017\",\"cbstaskid\":\"null\",\"strcpcesname\":\"null\",\"dtstarttime\":\"Mon Jun 12 11:18:28 CST 2017\",\"straccounttype\":\"null\",\"strreportmsg\":\"null\",\"strSelfTelenum\":\"null\",\"strbpname\":\"null\",\"irectifyfrontstatus\":\"null\",\"strInspectJournal1\":\"null\",\"strtransactorcertend\":\"null\",\"strInspectJournal2\":\"null\",\"dtcpcendtime\":\"null\",\"strpath\":\"null\",\"strcpcflownum\":\"null\",\"strRetCheck1\":\"null\",\"strRetCheck2\":\"null\",\"stragentcountry\":\"null\",\"queryFlag\":\"null\",\"strremark\":\"null\",\"servicestatus\":\"null\",\"stragentcerttype\":\"null\",\"strtransactorphone\":\"null\",\"isealpurpose\":\"null\",\"stragentphone\":\"null\",\"strrecheckoperaternum\":\"null\",\"strvouchertype\":\"null\",\"strcurrencylist\":\"null\",\"strbpreqid\":\"030000000020170612111620989\",\"strcertificatetype\":\"null\",\"strSelfCertificateNum\":\"idno1500000088\",\"chargename\":\"null\",\"strtransactorname\":\"null\",\"iviaCertImgCheckFlag\":\"null\",\"mistakeoperator\":\"null\",\"stroperaternum\":\"0300000000\",\"strbranchauthsn\":\"null\",\"strscenarioname\":\"个人综合开户及签约\",\"strcpcesnum\":\"null\",\"irepairscanstatus\":\"null\",\"strsnlogid2\":\"null\",\"strtransactorcerttype\":\"null\",\"strcurrencycode\":\"RMB\",\"strSelfName\":\"测试1500000088\",\"istayinspectreason\":\"null\",\"ichecksealflag\":\"null\",\"dtcpcstarttime\":\"null\",\"dtcpcouttime\":\"null\",\"strFrontTaskFailedReason\":\"null\",\"stragentcertbegin\":\"null\",\"strotheraccountname\":\"null\",\"strrectifyreason\":\"null\",\"strtsnum\":\"TS0001000001\",\"stragentcertno\":\"null\",\"idumpstatus\":\"null\",\"iamount2\":\"null\",\"strSelfNationality\":\"null\",\"strfrontmessage\":\"null\",\"strRenhangRetCheck1\":\"null\",\"strRenhangRetCheck2\":\"null\",\"chargecode\":\"null\",\"iservicetype\":\"1\",\"strotheraccountnum\":\"null\",\"strscenarionum\":\"SC00010000\",\"chargeamount\":\"null\",\"stresname\":\"ES-EX-IntegrateAccountCreate-T\",\"strscenarionums\":\"null\",\"stresnum\":\"ES-EX-IntegrateAccountCreate-T\",\"customerFingerData\":\"null\",\"idebitflag\":\"null\",\"strcertificatenum\":\"null\",\"strbranchnum\":\"0300\",\"stragentcertend\":\"null\",\"iprintflag\":\"null\",\"id\":\"1434255\",\"strproductcode\":\"993010000000000000\",\"ijournaltype\":\"null\",\"stragentname\":\"null\",\"strbptype\":\"null\",\"faceImagelogid\":\"null\",\"ilockflag\":\"0\",\"straccountnum\":\"null\",\"strtransactorcertno\":\"null\",\"strtradeaction\":\"30100010000301001000001\",\"iselfCertImgCheckFlag\":\"null\",\"strotherbankno\":\"null\",\"strbpnum\":\"1500000088\",\"istatisticstype\":\"null\",\"iservicestatus\":\"16\",\"strsnlogid\":\"30101201706120300519489\",\"iproxyCertImgCheckFlag\":\"null\",\"strSelfCertificateType\":\"25\"}")
//        .get)
//    println(jsonFunc("")
//        .get)
//    val arr2 = arr.map(line=>{
//      println(line)
//      println(jsonFunc(line))
//      jsonFunc(line)
//    })
//    arr2.filter({case None=>false; case Some(_)=>true}).foreach(println)
//    val json = arr2.filter({case None=>false; case Some(_)=>true}).map(_.get)
//    json.foreach(x=>println(x.get("customSerinum")))
////    json.foreach(x=>println(x.getString("customSerinu")==null))
//    val array = Array("a","b","c")
//    println(array.contains("a".asInstanceOf[Object]))
    val conf =ConfigFactory.load()
    val doOwnBusinessTsnum = conf.getString("xbank_strtsnum.do_own_business")
    val doOwnBusinessTsnumSplit = doOwnBusinessTsnum.split('|')
    doOwnBusinessTsnumSplit.foreach(println(_))
  }
}
