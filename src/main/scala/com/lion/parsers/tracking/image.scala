package com.lion.parsers.tracking

import util.parsers.tracking.WithJSoup


object image extends WithJSoup {
  override def scalectors: List[Selector] = List(("img.prod-img","src"))

}
