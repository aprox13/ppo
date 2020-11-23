package ru.ifkbhit.ppo5.darwing

import javafx.application.Application
import javafx.scene.canvas.{Canvas, GraphicsContext}
import javafx.scene.paint.Color
import javafx.scene.{Group, Scene}
import javafx.stage.Stage

class JavaFxDrawingApi extends Application with BaseDrawingApi {

  private implicit def toJfxColor(rgb: RGBColor): Color =
    Color.rgb(rgb.red, rgb.green, rgb.blue)

  override def start(primaryStage: Stage): Unit = {
    val group = new Group()
    val canvas = new Canvas(getCanvasWidth, getCanvasHeight)

    val context = canvas.getGraphicsContext2D
    JavaFxStorage.runActions(context)

    group.getChildren.add(canvas)
    primaryStage.setScene(new Scene(group))
    primaryStage.show()
  }


  override def drawCircle(center: Point, radius: Float, color: RGBColor): Unit =
    JavaFxStorage.addAction { graphicsContext =>
      graphicsContext.setFill(color)
      graphicsContext.fillOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
    }

  override def drawSegment(p1: Point, p2: Point, color: RGBColor): Unit =
    JavaFxStorage.addAction { graphicsContext =>
      graphicsContext.setFill(color)
      graphicsContext.strokeLine(p1.x, p1.y, p2.x, p2.y)
    }

  override def drawArrow(p1: Point, p2: Point, color: RGBColor): Unit = {
    if (p1 != p2) {
      drawSegment(p1, p2, color)
      JavaFxStorage.addAction { g =>
        val (xx, yy) = PointUtils.getArrowHead(p1, p2)
        g.fillPolygon(xx.map(_.toDouble), yy.map(_.toDouble), xx.length)
      }
    }
  }

  override def run(): Unit =
    Application.launch(classOf[JavaFxDrawingApi])
}

object JavaFxStorage extends ActionsStorage[GraphicsContext]