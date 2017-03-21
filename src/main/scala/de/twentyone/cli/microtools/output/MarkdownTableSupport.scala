package de.twentyone.cli.microtools.output

import shapeless.{Nat, Sized}

class MarkdownTableSupport[N <: Nat](dimensions: Sized[Seq[Int], N]) {
  def headerBottom: String = dimensions.map("-" * _).mkString("| ", " | ", " |")

  def contentLine(sections: Sized[Seq[String], N]): String =
    (sections zip dimensions)
      .map {
        case (box: String, length: Int) ⇒
          String.format(s"%-${length}s", box)
      }
      .mkString("| ", " | ", " |")

}
