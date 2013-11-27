package com.lion.parsers.discovery


object subcatNav extends DiscoveryJSoup {
  override def scalectors: List[Selector] = List(("a","href"))

}
