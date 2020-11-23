package ru.ifkbhit.ppo5

import java.io.File

import ru.ifkbhit.ppo5.darwing.{AwtDrawingApi, JavaFxDrawingApi}
import ru.ifkbhit.ppo5.graph.VerticesGraph

object Main {
  def main(args: Array[String]): Unit = {

    val file = new File(getClass.getClassLoader.getResource("vertices.graph").getPath)

    val api = AwtDrawingApi

    val g = VerticesGraph(file, api, 200)
    g.draw()


    api.run()
  }
}

