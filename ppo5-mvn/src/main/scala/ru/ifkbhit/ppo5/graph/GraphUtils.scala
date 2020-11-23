package ru.ifkbhit.ppo5.graph

import ru.ifkbhit.ppo5.darwing.Point

import scala.annotation.tailrec
import scala.math.BigDecimal.RoundingMode
import scala.math.{Pi, cos, sin}

object GraphUtils {

  private val PiHalf: BigDecimal = BigDecimal(math.Pi) / BigDecimal(2.toDouble)
  private val _2Pi: BigDecimal = BigDecimal(Pi) * BigDecimal(2)
  private val EPS: Int = 7


  /*
  (n - 2) * PI
  ------------------
      n
   */
  private def getRegularPolygonAngleRadian(n: Int): BigDecimal =
    (BigDecimal.valueOf(math.Pi) * (n - 2)) / BigDecimal(n)

  /**
   * Генерация координат вершин правильного многоугольника,
   * которые лежат на окружности c радиусом distance и центром p0
   *
   * @param n        количество вершин
   * @param p0       центр окружности
   * @param distance - радиус окружности, описанной около многоугольника
   * @return пустой список, если n <= 0
   *         p0, если n == 1
   *         две точки на концах отрезка длины 2 * distance с центром в p0, если n == 2
   *         иначе вершины многоугольника
   */
  def generateRegularPolygonPoints(n: Int, p0: Point, distance: Double): Seq[Point] =
    n match {
      case x if x <= 0 =>
        Seq.empty
      case 1 =>
        Seq(p0)
      case 2 =>
        val delta = Point(distance, 0)
        Seq(p0 - delta, p0 + delta)
      case n =>
        val stepAngle: BigDecimal = BigDecimal(Pi) - getRegularPolygonAngleRadian(n)

        val angles = Iterator.iterate(BigDecimal.valueOf(0.toDouble))(_ + stepAngle)
          .map(x => x.setScale(EPS, RoundingMode.CEILING).rounded)
          .takeWhile(_ <= 2 * Pi)
          .toSeq

        require(angles.size == n, s"Check EPS. For $EPS calculated ${angles.size} angels for n=$n")

        angles.map(makePoint(p0, _, distance))
    }

  private def makePoint(p0: Point, alpha: BigDecimal, distance: Double) =
    p0 + getTransformPoint(alpha) * distance


  @tailrec
  private def normalizeAngle(radians: BigDecimal): BigDecimal =
    if (radians < 0) {
      normalizeAngle(radians + _2Pi)
    } else if (radians > _2Pi) {
      normalizeAngle(radians - _2Pi)
    } else {
      radians
    }

  private def getTransformPoint(alpha: BigDecimal): Point =
    normalizeAngle(alpha.toDouble).toDouble match {
      case a if a >= 0 && a <= PiHalf =>
        Point(-cos(a), sin(a))
      case a if a > PiHalf && a <= Pi =>
        val x = (Pi - alpha).toDouble
        Point(cos(x), sin(x))
      case a if a > Pi && a <= Pi + PiHalf =>
        val x = (alpha - Pi).toDouble
        Point(cos(x), -sin(x))
      case _ => // a > Pi + PiHalf && a <= 2*Pi
        val x = (_2Pi - alpha).toDouble
        Point(-cos(x), -sin(x))
    }

  def main(args: Array[String]): Unit = {
    println(generateRegularPolygonPoints(4, Point(0, 0), 5))
  }
}
