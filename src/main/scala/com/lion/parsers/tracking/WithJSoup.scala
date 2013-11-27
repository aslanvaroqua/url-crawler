package util.parsers.tracking

import org.jsoup.nodes.{Element, Document}
import org.jsoup.Jsoup
import scala.Some
import scala.collection.JavaConverters._

/**
 * Created with IntelliJ IDEA.
 * User: miguelaiglesias
 * Date: 7/26/13
 * Time: 4:39 PM
 */
//add description css
//must have a function that returns
class WithJSoup extends ProductQuery[Option[Document]] {

  implicit def elements2Strings(elements: List[Element]) = elements.map(_.text())

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

  def apply(link: String) = {
    implicit val doc = documentSoup(link)

      queryString(scalectors)
  }

//  def metaClass: String = meta.parser
}