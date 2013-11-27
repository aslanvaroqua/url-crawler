package com.lion.parsers.discovery


object topNav extends DiscoveryJSoup {
  override def scalectors: List[Selector] = List("#primary-nav li a")
  override def text: List[Selector] = List("#primary-nav li a h2")
}
