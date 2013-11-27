package util.tracking

import java.util.{List, ArrayList}
import scala.concurrent.Future
import scala.Predef._
import util.parsers._
import util.persistance.RedisFactory._

class Tracking(url: String, retailer: String) {

//  type Url = String
//
//  type Retailer = String
//
//  def persistToDisco(url: Url, retailer: Retailer) = {
//    val retailer_prefix = "http://www.abercrombie.com"
//    val newUrl = links(url, retailer_prefix)
//
//
//  }
//
//  def retrievePage(url: Url): List[Url] = ???
//
//  def checkExisting(url: Url, retailer: Retailer): Future[Boolean] = client.sismember(retailer, url)
//
//
//  def execute(): Object = {
//
//    println("load url for - " + url)
//    val data = ???
//    val newUrls: List[String] = new ArrayList[String]()
//    println("load url for - " + url)
//
//    val existsFuture = checkExisting(url, retailer)
//
//    existsFuture onSuccess {
//
//      case true => ???
//
//      case false => {
//
//        persistToDisco(url, retailer)
//
//      }
//
//    }
//
//
////    existsFuture onFailure {
////
////      case true => {
////
////      }
////
////      case false => {
////
////      }
////
////    }
//
//
//    // Add original url to redis_visited database
//    // Check List to see if it is in redis_visited
//    // if not Pass new URLS back to Master
//
//
//    data
//  }
}