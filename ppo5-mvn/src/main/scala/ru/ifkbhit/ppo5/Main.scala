package ru.ifkbhit.ppo5

import java.io.File

import ru.ifkbhit.ppo5.darwing.{AwtDrawingApi, JavaFxDrawingApi}
import ru.ifkbhit.ppo5.graph.{MatrixGraph, VerticesGraph}

object Main {


  def main(args: Array[String]): Unit = {

    val vFile = new File(getClass.getClassLoader.getResource("vertices.graph").getPath)
    val mFile = new File(getClass.getClassLoader.getResource("matrix.graph").getPath)


//    val api = AwtDrawingApi
    val api = new JavaFxDrawingApi

//    val g = VerticesGraph(vFile, api, 200)
    val g = MatrixGraph(mFile, api, 200)
    g.draw()


    api.run()
  }
}

