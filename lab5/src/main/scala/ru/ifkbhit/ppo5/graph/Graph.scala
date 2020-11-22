package ru.ifkbhit.ppo5.graph

import ru.ifkbhit.ppo5.darwing.RGBColor._
import ru.ifkbhit.ppo5.darwing.{DrawingApi, RGBColor}

case class Vertex(id: Int)

case class Edge(from: Vertex, to: Vertex) {
  def reverse: Edge = Edge(to, from)
}


abstract class Graph {
  protected def drawingApi: DrawingApi

  protected def vertices: Set[Vertex]

  protected def edges: Set[Edge]

  protected def graphRadius: Int

  protected def verticesColors: Seq[RGBColor] = Seq(DeepPurple)

  protected def edgesColors: Seq[RGBColor] = Seq(Grey)

  def draw(): Unit = {
    val center = drawingApi.getCenterPoint

    val points = GraphUtils.generateRegularPolygonPoints(vertices.size, center, graphRadius)
    val vertexToPoint = vertices.zip(points).toMap
    val R = 10f

    val vertexColorIterator = getColorIterator(verticesColors)
    val edgesColorIterator = getColorIterator(edgesColors)

    edges.foreach { e =>
      val from = vertexToPoint(e.from)
      val to = vertexToPoint(e.to)

      val x = Geometry.lineCircleIntersect(to, R, from).getOrElse(to)

      drawingApi.drawArrow(from, x, edgesColorIterator.next())
    }

    vertexToPoint.values.foreach(drawingApi.drawCircle(_, 10f, vertexColorIterator.next()))
  }

  private def getColorIterator(color: Seq[RGBColor]): Iterator[RGBColor] =
    Iterator.from(0).map(i => color(i % color.size))
}


