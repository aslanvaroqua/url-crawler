package com.lion.persistance

/**
 * User: Maxim Matvienko
 * Date: 5/31/13
 * Time: 11:45 PM
 */

import com.mongodb.casbah.MongoConnection
import org.joda.time.DateTime
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers
import akka.actor.ActorSystem
import akka.util.Timeout
import com.redis.RedisClient

object MongoFactory {
  val connection: MongoConnection = MongoConnection("ops.lion.com", 27017)
  val connection_beta: MongoConnection = MongoConnection("ops.lion.com", 27017)
  val meta_db = connection("crawlers")

}

/**
 * User: Aslan Varoqua
 * Date: 9/19/2013
 * Time: 11:26 AM
 */

object RedisFactory {
  implicit val system = ActorSystem("redis-client")
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(5000)

  // Redis client setup
  val client = RedisClient("localhost", 6379)

}

