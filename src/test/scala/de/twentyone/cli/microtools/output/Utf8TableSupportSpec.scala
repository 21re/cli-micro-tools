package de.twentyone.cli.microtools.output

import org.scalatest.{MustMatchers, WordSpec}
import shapeless._

class Utf8TableSupportSpec extends WordSpec with MustMatchers {

  "Working utf8 table support" should {

    "basically work" in {
      val tableSupport = new Utf8TableSupport(Sized(5, 5))

      tableSupport.withSingleContentLine(Sized("1", "2")) mustEqual
        """┌─────┬─────┐
          |├─────┼─────┤
          |│1    │2    │
          |└─────┴─────┘""".stripMargin
    }
  }

  implicit class TableResultingOps[N <: Nat](table: Utf8TableSupport[N]) {
    def withSingleContentLine(line: Sized[Seq[String], N]): String = {
      s"""${table.headerTop}
         |${table.headerBottom}
         |${table.contentLine(line)}
         |${table.tableFooter}""".stripMargin
    }

  }

}
