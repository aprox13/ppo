package ru.ifkbhit.ppo5.graph

import java.io.File
import java.nio.file.Files

import ru.ifkbhit.ppo5.Utils
import ru.ifkbhit.ppo5.darwing.DrawingApi
import scala.collection.JavaConverters._

class VerticesGraph(size: Int, edgesRaw: Seq[(Int, Int)], override val drawingApi: DrawingApi, override val graphRadius: Int) extends Graph {

  edgesRaw.foreach { case (from, to) =>
    Seq(from, to).foreach { v =>
      require(v > 0, s"Expected vertex with id > 0, found $v at ${(from, to)}")
      require(v <= size, s"Expected vertex with id <= $size, found $v at ${(from, to)}")
    }
  }

  override protected def vertices: Set[Vertex] =
    (1 to size).map(Vertex).toSet

  override protected def edges: Set[Edge] =
    edgesRaw.map {
      case (from, to) =>
        Edge(Vertex(from), Vertex(to))
    }.toSet
}

object VerticesGraph {


  def apply(file: File, drawingApi: DrawingApi, graphRadius: Int): VerticesGraph = {
    val lines: Seq[String] = Files.readAllLines(file.toPath).asScala.toSeq
      .filter(_.nonEmpty)
    val n = lines.headOption.flatMap(Utils.parseIntSafe)
      .getOrElse(throw new RuntimeException(s"Expected count at first line, found ${lines.headOption.getOrElse("EMPTY_STRING")}"))

    val edges = lines.zipWithIndex.drop(1).map { case (l, idx) =>
      val ints = l.split(" ").map(_.trim).flatMap(Utils.parseIntSafe)

      require(ints.length == 2, s"Expected pair of ints in line ${idx + 1}, found: '$l'")

      val from = ints.head
      val to = ints.last

      (from, to)
    }

    new VerticesGraph(n, edges, drawingApi, graphRadius)
  }
}
