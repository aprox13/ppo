package ru.ifkbhit.ppo5.graph

import ru.ifkbhit.ppo5.darwing.Point

object Geometry {

  def lineCircleIntersect(circleCenter: Point, radius: Double, lineFrom: Point): Option[Point] = {
    val x0 = circleCenter.x
    val y0 = circleCenter.y
    val x1 = lineFrom.x
    val y1 = lineFrom.y

    val omega = (x0 - x1) / (y0 - y1)
    val k = x1 - omega * y1
    val d = k - x0


    def ySolve(negate: Boolean): Double = {
      val a = (if (negate) -1 else 1) * math.sqrt(
        -math.pow(d, 2) - 2 * d * omega * y0 + math.pow(radius * omega, 2) + math.pow(radius, 2) - math.pow(omega * y0, 2)
      ) - d * omega + y0

      a / (omega * omega + 1)
    }

    def xSolve(y: Double): Double =
      omega * y + k

    val (yR1, yR2) = (ySolve(false), ySolve(true))

    val res = Seq(
      Point(xSolve(yR1), yR1),
      Point(xSolve(yR2), yR2)
    )


    res.find { p =>
      between(p.x, x0, x1) && between(p.y, y0, y1)
    }
  }

  private def between(x: Double, b1: Double, b2: Double): Boolean = {
    val min = math.min(b1, b2)
    val max = math.max(b1, b2)

    min <= x && x <= max
  }
}
