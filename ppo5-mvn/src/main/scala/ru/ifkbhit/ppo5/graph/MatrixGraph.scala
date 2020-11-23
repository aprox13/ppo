package ru.ifkbhit.ppo5.graph

import java.io.File
import java.nio.file.Files
import java.util.stream.Collectors

import ru.ifkbhit.ppo5.Utils
import ru.ifkbhit.ppo5.darwing.DrawingApi
import ru.ifkbhit.ppo5.graph.MatrixGraph.NoEdge
import scala.collection.JavaConverters._

class MatrixGraph(matrix: Seq[Seq[Int]], override val drawingApi: DrawingApi, override val graphRadius: Int) extends Graph {

    matrix.zipWithIndex.foreach {
      case (row, idx) =>
        require(row.size == matrix.size,
          s"Expected quad matrix. Found matrix size: ${matrix.size} and row #${idx + 1} with size: ${row.size}")
    }


  override protected def vertices: Set[Vertex] =
    matrix.indices.map(Vertex).toSet

  override protected def edges: Set[Edge] =
    matrix.zipWithIndex.flatMap {
      case (row, i) =>
        row.zipWithIndex.flatMap {
          case (NoEdge, _) =>
            None
          case (_, j)=>
            Some(Edge(Vertex(i), Vertex(j)))
        }
    }.toSet

}

object MatrixGraph {
  val NoEdge: Int = 0
  val HasEdge: Int = 1

  def apply(file: File, drawingApi: DrawingApi, graphRadius: Int): MatrixGraph = {
    val matrix = Files.lines(file.toPath).collect(Collectors.toList[String])
      .asScala
      .filter(_.nonEmpty)
      .map(_.split(" "))
      .map(_.flatMap(Utils.parseIntSafe).toSeq)

    new MatrixGraph(matrix, drawingApi, graphRadius)
  }
}
