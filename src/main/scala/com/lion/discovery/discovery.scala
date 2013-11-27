package com.lion

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.Predef._
import scala.collection.immutable.List
import persistance.MongoFactory._
import persistance.RedisFactory._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import scala.annotation.tailrec
import parsers.discovery._
import akka.actor._

import scala.Some


class Discovery() {

  type Url = String

  type Link = String

  type Retailer = String

  type Domain = String

  def getLinks(url: Url, domain: Domain): List[Url] = {
    links(url, domain)
  }

  def checkExisting(retailer: Retailer)(url: Url): Boolean = {
    val retailer_visited = retailer + "-visited"
    Await.result(client.sismember(retailer_visited, url), 10 seconds)
  }

  def checkIfProduct(url: Url): Boolean = {
    url.contains("dp")
  }

  def style2(retailer: Retailer){
    val query = MongoDBObject("retailer_name" -> retailer)
    val coll = meta_db("retailers")
    val retailer_meta = coll.findOne(query).foreach {
      x =>
      // do some work if you found the retailer...
        val root_uri_option = x.getAs[String]("root_uri")
        val domain_option = x.getAs[String]("domain")
        val selectors = x.getAs[List[String]]("selectors")
        def show(x: Option[String]) = x match {
          case Some(s) => s
          case None => "?"
        }
        x match {
          case Some(s) => s
          case None => "?"
        val root_uri = show(root_uri_option)
        val domain = show(domain_option)
        recurse(root_uri, domain, retailer)
        val system = ActorSystem("MySystem")
        val topNavParser = system.actorOf(Props[topNavActor], name = "topnav")
        //send a message to get all top links...
        topNavParser ! Parse(root_uri, domain, retailer)
    }



  def startJob(retailer: Retailer) = {
    val query = MongoDBObject("retailer_name" -> retailer)
    val coll = meta_db("retailers")
    val retailer_meta = coll.findOne(query).foreach {
      x =>
      // do some work if you found the retailer...
        val root_uri_option = x.getAs[String]("root_uri")
        val domain_option = x.getAs[String]("domain")
        val selectors = x.getAs[List[String]]("selectors")
        def show(x: Option[String]) = x match {
          case Some(s) => s
          case None => "?"
        }
        val root_uri = show(root_uri_option)
        val domain = show(domain_option)
        recurse(root_uri, domain, retailer)
    }
  }

  def addToExisting(url: Url, retailer: Retailer) = client.sadd(url, retailer)

  def addToRedisURI(url: Url, retailer: Retailer) = client.sadd(url, retailer)

  def recurse(url: Url, domain: Domain, retailer: Retailer): Unit = {

    val visited_retailer = retailer + "-visited"
    val pageLinks = getLinks(url: Url, domain: Domain)
    val product_retailer = retailer + "-urls"

    def processURL(page: Url, domain: Domain) {
      page
      checkIfProduct(page) match {
        case true => {
          addToRedisURI(product_retailer, page)
          addToExisting(visited_retailer, page)
        }
        case false => addToExisting(visited_retailer, page)
      }

      val newLinks = getLinks(page, domain)
      func1(newLinks, domain)
    }

    func1(pageLinks, domain)

    @tailrec
    def func1(pageLinks: List[Url], domain: Domain): Unit = pageLinks.filterNot(checkExisting(retailer)).filterNot(_.contains("#")) match {
      case head :: Nil => processURL(head, domain)
      case head :: tail => processURL(head, domain)
        func1(tail, domain)
      case Nil => Unit
    }

  }
}
}

