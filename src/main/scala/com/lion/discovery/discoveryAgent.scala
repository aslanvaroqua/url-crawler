package com.lion.discovery

import com.lion.misc.Global
import javax.mail._
import javax.mail.internet.{MimeMessage, InternetAddress}
import java.util.{Properties, Date}
import com.lion.parsers.discovery.topNav
import com.lion.discovery.CatActor
import java.util

/**
 * Created with IntelliJ IDEA.
 * User: avaroqua
 * Date: 9/23/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */

case class Input(url:String, domain:String, selectors:List[String])


class TopNavAgent(url:String, domain:String, selectors:List[String])  {
  def getTopNav = ???
  val map = scala.collection.mutable.HashMap.empty[String,String]
  val hello = new CatActor(url, domain, selectors, map)
}

class CategoryAgent(url:String, domain:String, selectors:List[String], map: scala.collection.mutable.HashMap[String,String])  {


}

