package ru.ifkbhit.ppo5.darwing

import java.awt.event.{WindowAdapter, WindowEvent}
import java.awt.geom.{Ellipse2D, Line2D}
import java.awt.{Color, Frame, Graphics, Graphics2D}

object AwtDrawingApi extends Frame with BaseDrawingApi with ActionsStorage[Graphics2D] {
  setVisible(true)
  setSize(getCanvasWidth, getCanvasHeight)

  private implicit def rgbToAwtColor(color: RGBColor): Color =
    new Color(color.red, color.green, color.blue)

  override def paint(g: Graphics): Unit =
    runActions(g.asInstanceOf[Graphics2D])

  this.addWindowListener {
    new WindowAdapter() {
      override def windowClosing(we: WindowEvent): Unit = {
        System.exit(0)
      }
    }
  }

  override def drawCircle(center: Point, radius: Float, color: RGBColor): Unit = {
    addAction { g =>
      val x = center.x - radius
      val y = center.y - radius
      g.setPaint(color)
      g.fill(new Ellipse2D.Double(x, y, 2 * radius, 2 * radius))
    }
  }

  override def drawSegment(p1: Point, p2: Point, color: RGBColor): Unit = {
    if (p1 != p2) {
      addAction { g =>
        g.setPaint(color)
        g.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y))
      }
    }
  }

  override def drawArrow(p1: Point, p2: Point, color: RGBColor): Unit = {
    if (p1 != p2) {
      drawSegment(p1, p2, color)
      addAction { g =>
        g.setPaint(color)
        val (xx, yy) = PointUtils.getArrowHead(p1, p2)
        g.fillPolygon(xx, yy, xx.length)
      }
    }
  }

  override def run(): Unit = repaint()
}
