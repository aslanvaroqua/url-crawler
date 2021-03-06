package com.lion.parsers.discovery

import scala.Some
import util.parsers.tracking.Extractors._
import util.models.RetailerModelLegacy._

trait LinksQuery[T] {

  //def metaCrawler:List[String]

  sealed trait Selector

  case class SelectorTag(tag: String) extends Selector

  case class SelectorAttribute(tag: String, attr: String) extends Selector

  implicit def string2Selector(sel: String): Selector = new SelectorTag(sel)

  implicit def tuple2Selector(sel: (String, String)) = new SelectorAttribute(sel._1, sel._2)

  def simpleQuery(query: String)(implicit driver: T): List[String]

  def simpleQuery(query: String, attr: String)(implicit driver: T): List[String]

  def preProcess(results: List[String]) = results


  def queryPrice(selectors: List[Selector])(implicit doc: T): Option[String] = selectors match {
    case x :: xs => query(x).filter(_.length > 0) match {
      case Nil => queryPrice(xs)
      case elements => Option(findPrice(preProcess(elements)).toString())

    }
    case _ => None
  }

  def queryString(selectors: List[Selector])(implicit doc: T): Option[String] = selectors match {
    case x :: xs => query(x).filter(_.length > 0) match {
      case Nil => queryString(xs)
      case elements => findString(preProcess(elements))
    }
    case _ => None
  }

  def queryUrl(selectors: List[Selector], retailer_prefix: String)(implicit doc: T): List[String] = selectors match {
    case x :: xs => query(x).filter(_.length > 0) match {
      case Nil => queryUrl(xs, retailer_prefix)
      case elements => findUrl(preProcess(elements), retailer_prefix)
    }
    case _ => None
  }

  //first find price selectors, then give me description selectors, construct the object
  def scalectors: List[Selector] = ???
  def text: List[Selector] = ???

  def findString(elements: List[String]): Option[String] = elements.foldLeft(Option("")) {
    (string, el) => string match {
      case _ => Some(defaultStringExtractor(el))
    }

  }



  def checkDomain(retailer_prefix: String)(url: String): Boolean = url.contains(retailer_prefix)


  def findLinks(elements: List[String]): Option[String] = elements.foldLeft(Option("")) {
    (string, el) => string match {
      case _ => Some(defaultStringExtractor(el))
    }

  }

  def findUrl(elements: List[String], retailer_prefix: String): List[String] = {
    elements.map(url => if (!url.startsWith("http")) retailer_prefix + url else url).filter(checkDomain(retailer_prefix))
  }


  def findPrice(elements: List[String]): Option[Float] = elements.foldLeft(Option(0F)) {
    (price, el) => price match {
      case value@Some(p: Float) if p > 0 => value
      case _ => defaultPriceExtractor(Some(el))
    }

  }

  def query(selector: Selector)(implicit driver: T): List[String] = selector match {
    case s: SelectorTag => simpleQuery(s.tag)
    case s: SelectorAttribute => simpleQuery(s.tag, s.attr)
  }
}

//
//  // Will take case class.. name of class some parameters
//object StoreQuery{
//  def apply[T] (retailer_id: String, filter:Filter): Option[JodaGridFSDBFile] = {
//    findRetailerById(retailer_id) match {
//      case Some(retailer) =>
//      case _ => None
//    }
//  }
//
//  }



