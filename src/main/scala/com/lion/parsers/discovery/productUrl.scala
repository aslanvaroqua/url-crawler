package com.lion.parsers.discovery


object productUrl extends DiscoveryJSoup {
  override def scalectors: List[Selector] = List(("a","href"))

}
