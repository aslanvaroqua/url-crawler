package com.lion.parsers.tracking

import util.parsers.tracking.WithJSoup


object title extends WithJSoup {
  override def scalectors: List[Selector] = List(".product-form .product-title .name")
}

