package util.parsers.tracking


trait WithSeleniumJavaScript extends WithSelenium {
  override def isJavaScriptEnabled: Boolean = true
}
