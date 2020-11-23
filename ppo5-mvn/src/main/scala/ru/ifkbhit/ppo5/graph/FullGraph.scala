package ru.ifkbhit.ppo5.graph

import ru.ifkbhit.ppo5.darwing.RGBColor.{BlueGrey, DeepPurple, Teal}
import ru.ifkbhit.ppo5.darwing.{DrawingApi, RGBColor}

case class FullGraph(size: Int, override val drawingApi: DrawingApi, override val graphRadius: Int) extends Graph {
  override protected def vertices: Set[Vertex] =
    (1 to size).map(Vertex).toSet

  override protected def edges: Set[Edge] = {
    val res = for {
      i <- 0 until size
      j <- 0 until size
    } yield Edge(vertices.toSeq(i), vertices.toSeq(j))

    res.toSet
  }

  override protected def edgesColors: Seq[RGBColor] = Seq(RGBColor.BlueGrey, RGBColor.Grey)

  override protected def verticesColors: Seq[RGBColor] = Seq(DeepPurple, BlueGrey, Teal)
}

class EdgeLessGraph(size: Int, override val drawingApi: DrawingApi, override val graphRadius: Int)
  extends FullGraph(size, drawingApi, graphRadius) {
  override protected def edges: Set[Edge] = Set.empty
}

