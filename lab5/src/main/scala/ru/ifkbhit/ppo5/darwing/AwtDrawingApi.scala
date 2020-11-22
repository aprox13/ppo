package ru.ifkbhit.ppo5.darwing

import java.awt.{Color, Frame, Graphics, Graphics2D}
import java.awt.event.{WindowAdapter, WindowEvent}
import java.awt.geom.{Ellipse2D, Line2D}

import scala.collection.mutable

class AwtDrawingApi(width: Int, height: Int) extends Frame with DrawingApi {
  setVisible(true)
  setSize(width, height)

  private implicit def rgbToAwtColor(color: RGBColor): Color =
    new Color(color.red, color.green, color.blue)

  private val actions: mutable.Buffer[Graphics2D => Unit] = mutable.Buffer.empty[Graphics2D => Unit]

  override def paint(g: Graphics): Unit = {

    val graphics: Graphics2D = g.asInstanceOf[Graphics2D]
    require(actions != null)
    if (graphics != null) {
      actions.filter(_ != null).foreach { a =>
        a(graphics)
      }
    }
  }

  this.addWindowListener {
    new WindowAdapter() {
      override def windowClosing(we: WindowEvent): Unit = {
        System.exit(0)
      }
    }
  }


  private def addAction(action: Graphics2D => Unit): Unit = actions += action

  override def drawCircle(center: Point, radius: Float, color: RGBColor): Unit = {
    addAction { g =>
      val x = center.x - radius
      val y = center.y - radius
      g.setPaint(color)
      g.fill(new Ellipse2D.Double(x, y, 2 * radius, 2 * radius))
    }
  }

  override def getCanvasWidth: Int = getWidth

  override def getCanvasHeight: Int = getHeight

  override def drawSegment(p1: Point, p2: Point, color: RGBColor): Unit = {
    if (p1 != p2) {
      addAction { g =>
        g.setPaint(color)
        g.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y))
      }
    }
  }

  override def getCenterPoint: Point = Point(getCanvasWidth / 2, getCanvasHeight / 2)

  override def drawText(text: String, point: Point): Unit = {
    addAction { g =>
      g.drawString(text, point.x.toFloat, point.y.toFloat)
    }
  }

  override def drawArrow(p1: Point, p2: Point, color: RGBColor): Unit = {
    if (p1 != p2) {
      drawSegment(p1, p2, color)
      addAction { g =>
        drawArrowHead(g, p1, p2)
      }
    }
  }

  private def drawArrowHead(g: Graphics2D, p1: Point, p2: Point, h: Int = 7): Unit = {
    val dp = p2 - p1
    val D = dp.vectorLength
    val C = D - 7

    val a = Point(C, h)
    val b = Point(C, -h)

    def formula(p: Point, from: Point): Point = {
      val sin = dp.y / D
      val cos = dp.x / D

      Point(
        p.x * cos - p.y * sin + from.x,
        p.x * sin + p.y * cos + from.y
      )
    }

    val m = formula(a, p1)
    val n = formula(b, p1)


    val (xx, yy) = PointUtils.splitPointsToInt(p2, m, n)

    g.fillPolygon(xx, yy, 3)
  }

}
