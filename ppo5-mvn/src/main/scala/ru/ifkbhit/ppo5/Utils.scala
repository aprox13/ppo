package ru.ifkbhit.ppo5

import scala.util.Try

object Utils {
  def parseIntSafe(x: String): Option[Int] =
    Try(x.trim.toInt).toOption
}
