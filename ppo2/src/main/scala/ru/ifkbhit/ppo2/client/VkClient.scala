package ru.ifkbhit.ppo2.client

import ru.ifkbhit.ppo2.model.request.NewsFeedRequest
import ru.ifkbhit.ppo2.model.response.{VkCountResponse, VkResponse}

trait VkClient {
  def getNewsCountByTag(request: NewsFeedRequest): VkCountResponse
  def getNewsFeeds(request: NewsFeedRequest): VkResponse
}
