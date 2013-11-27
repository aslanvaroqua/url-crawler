package util.parsers.tracking

import scala.util.matching.Regex

/**
 * User: aslan varoqua
 * Date: 8/30/13
 * Time: 9:38 AM
 */
object Extractors {

  sealed trait Extractor


  def ExtractorSelector(name: String): Extractor = name match {
    case "default" => defaultStringExtractor
    case "imageUrlScheme" => SchemeImageExtractor
    case "priceExtractor" => defaultPriceExtractor
  }

  def trim(stringRaw: String) = stringRaw.trim


  //  case object defaultStringExtractor extends Extractor {
  //    def apply(stringRaw: String): String = {
  //      val finalString = trim(stringRaw)
  //      finalString
  //    }
  //  }

  def stringMatcher: Regex = "[0-9.]*".r

  //Add REGEX for http schemes

  case object defaultStringExtractor extends Extractor {
    def apply(stringRaw: String): String = {

      val finalString = trim(stringRaw)
      finalString

    }
  }


  case object SchemeImageExtractor extends Extractor {
    def apply(stringRaw: String): String = {

      val finalString: String = "http:" + trim(stringRaw)
      finalString
    }
  }

  def matcher: Regex = "[0-9.]*".r

  case object defaultPriceExtractor extends Extractor {
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


//image_uri.scheme = 'http'
//  image_uri.query = '$anfProductImageMagnify$'
//}


