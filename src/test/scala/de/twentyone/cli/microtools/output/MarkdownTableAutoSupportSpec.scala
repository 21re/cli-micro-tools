package de.twentyone.cli.microtools.output

import org.scalatest.{MustMatchers, WordSpec}
import shapeless.Sized

class MarkdownTableAutoSupportSpec extends WordSpec with MustMatchers {

  "Working markdown table support" should {

    "basically work" in {
      val tableSupport = new MarkdownTableAutoSupport(Sized("A", "B"))

      tableSupport
        .contentLines(Seq(Sized("1", "2"), Sized("100", "200"), Sized("12345", "23456")))
        .mkString("\n") mustEqual
        """| A     | B     |
          #| ----- | ----- |
          #| 1     | 2     |
          #| 100   | 200   |
          #| 12345 | 23456 |""".stripMargin('#')
    }
  }

}
