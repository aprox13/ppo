package ru.ifkbhit.ppo2

import org.joda.time.DateTime
import ru.ifkbhit.ppo2.client.VkClient
import ru.ifkbhit.ppo2.model.TimeRange
import ru.ifkbhit.ppo2.model.request.NewsFeedRequest

trait NewsStatistic {
  def getCountsInDay(query: String, end: DateTime): Seq[Int]
}

class DefaultNewsStatistic(vkClient: VkClient) extends NewsStatistic {
  override def getCountsInDay(query: String, end: DateTime): Seq[Int] = {
    require(query.nonEmpty, "Empty query")

    (0 until 24)
      .map(end.minusHours)
      .map(to => to.minusHours(1) -> to)
      .map(TimeRange.apply)
      .map(NewsFeedRequest(query, _, count = 0))
      .map(vkClient.getNewsCountByTag(_).totalCount)
  }
}
