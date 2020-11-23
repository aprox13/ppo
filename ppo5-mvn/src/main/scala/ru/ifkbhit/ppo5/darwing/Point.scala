package ru.ifkbhit.ppo5.darwing

case class Point(x: Double, y: Double) {

  def +(point: Point): Point =
    copy(
      x = x + point.x,
      y = y + point.y
    )

  def *(a: Double): Point = copy(x = a * x, y = a * y)

  def -(point: Point): Point = this + (point * -1)

  def distance(point: Point): Double =
    math.sqrt(
      Seq(point.x - x, point.y - y).map(math.pow(_, 2))
        .sum
    )

  def vectorLength: Double = distance(Point(0, 0))
}

