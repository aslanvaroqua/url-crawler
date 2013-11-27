package util.parsers.tracking

import scala.util.matching.Regex

/**
 * User: avaroqua
 * Date: 8/30/13
 * Time: 9:38 AM
 */
object PriceExtractors {

  sealed trait PriceExtractor


  def priceExtractorSelector(name: String): PriceExtractor = name match {
    case "default" => defaultPriceExtractor
  }

  def matcher: Regex = "[0-9.]*".r

  case object defaultPriceExtractor extends PriceExtractor {
    def apply(priceRaw: Option[String]): Option[Float] = {

      val finalPrice = priceRaw match {
        case Some(price) =>
          val matches = matcher.findAllMatchIn(price)
          matches.foldLeft("")(_ + _.matched) match {
            case "" => None
            case result => try
              Some(result.toFloat)
            catch {
              case ex: Exception =>
                println(priceRaw + " - " + result)
                None
            }
          }
        case _ => None
      }
      finalPrice
    }
  }

}
