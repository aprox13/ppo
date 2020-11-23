package ru.ifkbhit.ppo5.darwing

object PointUtils {

  def splitPointsToInt(points: Point*): (Array[Int], Array[Int]) = {
    val xx = points.map(_.x.toInt).toArray
    val yy = points.map(_.y.toInt).toArray

    (xx, yy)
  }

  def getArrowHead(p1: Point, p2: Point, h: Int = 7): (Array[Int], Array[Int]) = {
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


    val (xx, yy) = splitPointsToInt(p2, m, n)

    (xx, yy)
  }
}
