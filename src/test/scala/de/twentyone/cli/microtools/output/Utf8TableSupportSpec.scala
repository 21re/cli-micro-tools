package de.twentyone.cli.microtools.output

import org.scalatest.{MustMatchers, WordSpec}
import shapeless._

class Utf8TableSupportSpec extends WordSpec with MustMatchers {

  "Working utf8 table support" should {

    "basically work" in {
      val tableSupport = new Utf8TableSupport(Sized(5, 5))

      // format: off
      tableSupport.headerTop mustEqual                            "┌─────┬─────┐"
      tableSupport.headerBottom mustEqual                         "├─────┼─────┤"
      tableSupport.contentLine(Sized("1234", "5432")) mustEqual   "│1234 │5432 │"
      tableSupport.tableFooter mustEqual                          "└─────┴─────┘"
      // format: on
    }

  }

}
