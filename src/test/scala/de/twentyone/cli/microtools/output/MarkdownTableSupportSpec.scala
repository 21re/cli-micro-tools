package de.twentyone.cli.microtools.output

import org.scalatest.{MustMatchers, WordSpec}
import shapeless.{Nat, Sized}

class MarkdownTableSupportSpec extends WordSpec with MustMatchers {

  "Working markdown table support" should {

    "basically work" in {
      val tableSupport = new MarkdownTableSupport(Sized(5, 5))

      tableSupport.withSingleContentLine(Sized("A", "B"), Sized("1", "2")) mustEqual
        """| A     | B     |
          #| ----- | ----- |
          #| 1     | 2     |""".stripMargin('#')
    }

    "not cut overflow " in {
      val tableSupport = new MarkdownTableSupport(Sized(5, 5))

      tableSupport.withSingleContentLine(Sized("A", "B"), Sized("123456789", "2")) mustEqual
        """| A     | B     |
          #| ----- | ----- |
          #| 123456789 | 2     |""".stripMargin('#')
    }
  }

  implicit class TableResultingOps[N <: Nat](table: MarkdownTableSupport[N]) {
    def withSingleContentLine(titles: Sized[Seq[String], N], line: Sized[Seq[String], N]): String = {
      s"""${table.contentLine(titles)}
         #${table.headerBottom}
         #${table.contentLine(line)}""".stripMargin('#')
    }

  }

}
