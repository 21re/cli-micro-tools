package de.twentyone.cli.microtools.output

import java.nio.charset.StandardCharsets
import shapeless._

// N is the amount of horizontal cells in the table (a Nat) while the ints are the widths of the cells
// For example Utf8TableSupport[Nat(3)](Sized(3,2,3)) is a table with 3 cells of widths 3, 2 and 3.
class Utf8TableSupport[N <: Nat](dimensions: Sized[Seq[Int], N]) {
  def headerTop: String    = dimensions.map("─" * _).mkString("┌", "┬", "┐")
  def headerBottom: String = dimensions.map("─" * _).mkString("├", "┼", "┤")
  def tableFooter: String  = dimensions.map("─" * _).mkString("└", "┴", "┘")

  def contentLine(sections: Sized[Seq[String], N]): String =
    (sections zip dimensions)
      .map {
        case (box: String, length: Int) ⇒
          val boxInTerminal = box.replaceAll("\u001B\\[[\\d;]*[^\\d;]", "")
          val byteLength    = boxInTerminal.getBytes(StandardCharsets.UTF_8).length
          // the amount of chars that are not rendered to a terminal due to being ansi escapes
          val unrenderedLength = box.length - boxInTerminal.length
          // with this we accomodate chars that are two byte wide by padding with one less space
          // this will probably be an issue with chars that are three bytes wide
          val additionalLengthDueToWideSymbols = (byteLength - boxInTerminal.length) / 2
          val boxWithLeadingSpace              = s" $box"
          String.format(s"%-${length + unrenderedLength + additionalLengthDueToWideSymbols}s",
                        boxWithLeadingSpace)
      }
      .mkString("│", "│", "│")
}
