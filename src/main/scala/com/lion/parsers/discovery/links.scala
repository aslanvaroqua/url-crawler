package com.lion.parsers.discovery


object links extends DiscoveryJSoup {
  override def scalectors: List[Selector] = List(("a","href"))

}
