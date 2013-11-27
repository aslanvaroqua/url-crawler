package com.lion.parsers.discovery

import org.jsoup.nodes.{Element, Document}
import org.jsoup.Jsoup
import scala.Some
import scala.collection.JavaConverters._
import util.parsers.discovery.LinksQuery

/**
 * Created with IntelliJ IDEA.
 * User: avaroqua
 * Date: 7/26/13
 * Time: 4:39 PM
 */
//add description css
//must have a function that returns
class DiscoveryJSoup extends LinksQuery[Option[Document]] {

  implicit def elements2Strings(elements: List[Element]) = elements.map(_.text())

  type Url = String

  type Retailer = String

  type RetailerPrefix = String

  def simpleQuery(query: String)(implicit document: Option[Document]): List[String] = document match {
    case Some(doc: Document) => doc.select(query).asScala.toList
    case None => List[String]()
  }

  def simpleQuery(query: String, attr: String)(implicit document: Option[Document]): List[String] = document match {
    case Some(doc: Document) => doc.select(query).asScala.toList.map(_.attr(attr))
    case None => List[String]()
  }

  def documentSoup(link: String): Option[Document] = try
    Some(Jsoup.connect(link).timeout(60000).get())
  catch {
    case ex: Exception =>
      println(ex.getMessage)
      None
  }


  def apply(url: Url, retailer_prefix: RetailerPrefix) = {
    implicit val doc = documentSoup(url)

      queryUrl(scalectors, retailer_prefix)
  }

//  def metaClass: String = meta.parser
}