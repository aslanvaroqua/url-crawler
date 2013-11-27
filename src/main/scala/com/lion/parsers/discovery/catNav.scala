package com.lion.parsers.discovery


object catNav extends DiscoveryJSoup {
  override def scalectors: List[Selector] = List(("a","href"))

}
