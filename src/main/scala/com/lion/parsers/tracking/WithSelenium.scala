package util.parsers.tracking

import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import com.gargoylesoftware.htmlunit.BrowserVersion
import org.scalatest.concurrent.Eventually._
import org.scalatest.selenium.WebBrowser


trait WithSelenium extends ProductQuery[WebDriver] with WebBrowser {
  def isJavaScriptEnabled: Boolean = false


  def apply(link: String): Option[String] = {
    implicit val driver = createDriver
    val result = try {
      goLink(link)
      queryPrice(scalectors)
    }
    catch {
      case ex: Exception =>
        println(ex.getMessage)
        None
    }
    close
    result
  }


  def createDriver: WebDriver = {
    //      new FirefoxDriver()
    //      new HtmlUnitDriver(BrowserVersion.FIREFOX_17)
    val driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_17)
    driver.setJavascriptEnabled(isJavaScriptEnabled)
    driver
  }

  def goLink(link: String)(implicit driver: WebDriver = new HtmlUnitDriver()): Unit = {
    go.to(link)
    Thread.sleep(5000)
  }

  def querySelenium(selector: String)(implicit driver: WebDriver): List[Element] = {
    val results  = eventually(findAll(cssSelector(selector)).toList)
    results
  }

  def simpleQuery(selector: String)(implicit driver: WebDriver): List[String] = querySelenium(selector) match {
    case elements: List[Element] =>
      val texts = elements.map(_.text)
      texts
    case _ => List[String]()
  }

  def simpleQuery(selector: String, attribute: String)(implicit driver: WebDriver): List[String] = querySelenium(selector) match {
    case elements: List[Element] => elements.flatMap(_.attribute(attribute))
    case _ => List[String]()
  }

}
