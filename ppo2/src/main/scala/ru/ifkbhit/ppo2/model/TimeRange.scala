package ru.ifkbhit.ppo2.model

import org.joda.time.DateTime

case class TimeRange(from: DateTime, to: Option[DateTime] = None) {
  def fromToMillis: Long = from.getMillis
  def toToMillisOrNow: Long = to.getOrElse(DateTime.now()).getMillis
}

object TimeRange {
  def apply(tp: (DateTime, DateTime)): TimeRange = new TimeRange(tp._1, Some(tp._2))
}


