package ru.ifkbhit.ppo5.darwing

object PointUtils {


  def splitPointsToInt(points: Point*): (Array[Int], Array[Int]) = {
    val xx = points.map(_.x.toInt).toArray
    val yy = points.map(_.y.toInt).toArray

    (xx, yy)
  }
}
