package com.lion.parsers.discovery

object topNavText extends DiscoveryJSoup {
  override def scalectors: List[Selector] = List("#primary-nav li a h2")

}
