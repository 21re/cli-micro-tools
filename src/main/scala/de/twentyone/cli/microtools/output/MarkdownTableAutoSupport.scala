package de.twentyone.cli.microtools.output

import shapeless.{Nat, Sized}

class MarkdownTableAutoSupport[N <: Nat](titles: Sized[Seq[String], N]) {
  def contentLines(rows: Seq[Sized[Seq[String], N]]): Seq[String] = {
    val dimensions = rows.foldLeft(titles.map(_.length)) {
      case (dimensions, row) =>
        (dimensions zip row).map {
          case (dimension, cell) if dimension < cell.length => cell.length
          case (dimension, _)                               => dimension
        }
    }

    val headerTop = (titles zip dimensions)
      .map {
        case (box: String, length: Int) ⇒
          String.format(s"%-${length}s", box)
      }
      .mkString("| ", " | ", " |")
    val headerBottom = dimensions.map("-" * _).mkString("| ", " | ", " |")

    headerTop +: headerBottom +: rows.map { row =>
      (row zip dimensions)
        .map {
          case (box: String, length: Int) ⇒
            String.format(s"%-${length}s", box)
        }
        .mkString("| ", " | ", " |")
    }
  }
}
