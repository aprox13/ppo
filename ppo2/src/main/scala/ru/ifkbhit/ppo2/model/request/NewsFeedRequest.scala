package ru.ifkbhit.ppo2.model.request

import ru.ifkbhit.ppo2.model.TimeRange

case class NewsFeedRequest(query: String, timeRange: TimeRange, count: Int)
