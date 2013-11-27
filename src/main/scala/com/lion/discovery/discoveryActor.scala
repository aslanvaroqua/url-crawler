package com.lion

import akka.actor._
import scala.collection.immutable.List
import com.lion.Parse
import com.lion.NavBar
import scala.Predef._
import com.lion.Parse
import com.lion.NavBar
import com.lion.parsers.discovery.topNav

case class Parse(url: String, domain: String, retailer:String)

case class ParseSubandCat(link:String, url:String, retailer:String)

case class NavBar(label:String,url:String)

case object Done

class topNavActor extends Actor with ActorLogging {

  def parse(url: String, domain: String, retailer: String):List[NavBar] = {
   val link:Link[String] = topNav(url, domain)


   val label = topNav(url, domain)



   val c:List[NavBar] = (link, label)

  }

  var totalWorking =   0

  def receive = {
    case Done => {
      totalWorking =   totalWorking  - 1
      if (totalWorking == 0) sender ! PoisonPill
    }

    case Parse(url, domain, retailer) ⇒ {
      //get topNav from Database

      val topNav:List[NavBar] = parse(url,domain, retailer)
      totalWorking =  topNav.size
      topNav.foreach{
        tn =>
          val next = context.actorOf(Props[catNavActor], name = "topnav")
          val linkMap = tn.label
          val retailer_string = tn.retailer
          next ! ParseSubandCat(tn.label, tn.url, retailer) //add hash here
      }

    }
  }
}


class catNavActor extends Actor with ActorLogging {
  var totalWorking =   0
  def parse(linkMap:Map, retailer:String) = ???

  def receive = {
    case Done => {
      totalWorking =   totalWorking  - 1
      if (totalWorking == 0) sender ! Done
    }


    case ParseSubandCat(label, link, retailer) ⇒ {

      //parse and with each link and key value
      //get catNav selector from database
      //val catNav:List[NavBar] = parse(url,domain,retailer, data)
      //totalWorking =  catNav.size
     // catNav.foreach{
//        tn =>
//
//          val next = context.actorOf(Props[subNavActor], name = "catnav")
//
//          //check for subcat if so parse, if not write to redis urls
//          next ! Parse(url, domain, retailer) //add hash here
//      }

    }
}
}

class subNavActor extends Actor with ActorLogging {
  var totalWorking =   0
  def parse(url: String, domain: String, retailer: String) = ???

  def receive = {
    case Done => {
      totalWorking =   totalWorking  - 1
      if (totalWorking == 0) sender ! Done
    }


    case Parse(url, domain, selectors) ⇒ {
      //parse and with each link and key value

      val subNav:List[NavBar] = parse(url,domain,retailer)
      totalWorking =  subNav.size
      subNav.foreach{
        tn =>

          val next = context.actorOf(Props[catNavActor], name = "subnav")
          next ! Parse(url, domain, selectors) //add hash here
      }

    }
  }
}

