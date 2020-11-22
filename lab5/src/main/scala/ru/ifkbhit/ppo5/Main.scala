package ru.ifkbhit.ppo5

import java.io.File

import ru.ifkbhit.ppo5.darwing.AwtDrawingApi
import ru.ifkbhit.ppo5.graph.{FullGraph, MatrixGraph, VerticesGraph}



object Main {
  def main(args: Array[String]): Unit = {

    val file = new File(getClass.getClassLoader.getResource("vertices.graph").getPath)

    val api = new AwtDrawingApi(800, 800)

    val g = VerticesGraph(file, api, 200)
    g.draw()


    api.repaint()
  }
}

