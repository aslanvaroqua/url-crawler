package com.lion.parsers.tracking

import util.parsers.tracking.WithJSoup


object price extends WithJSoup {
  override def scalectors: List[Selector] = List("div.price[data-priceflag] > h4.offer-price")

}

