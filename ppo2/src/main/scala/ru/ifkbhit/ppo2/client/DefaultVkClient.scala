package ru.ifkbhit.ppo2.client
import ru.ifkbhit.ppo2.client.http.{HttpRequestProvider, JsonFormat, JsonToModel}
import ru.ifkbhit.ppo2.config.VkConfig
import ru.ifkbhit.ppo2.model.request.NewsFeedRequest
import ru.ifkbhit.ppo2.model.response.{VkCountResponse, VkResponse}
import ru.ifkbhit.ppo2.util.http.HttpUtils
import ru.ifkbhit.ppo2.util.http.HttpUtils.StringOps

class DefaultVkClient(httpRequestProvider: HttpRequestProvider, vkConfig: VkConfig) extends VkClient {

  override def getNewsCountByTag(request: NewsFeedRequest): VkCountResponse =
    getNewsFeeds(request).response

  override def getNewsFeeds(request: NewsFeedRequest): VkResponse = {
    httpRequestProvider.getForUrl(vkConfig.host.withPath(NewsFeedPath))
      .param("v", vkConfig.version)
      .param("access_token", vkConfig.accessToken)
      .param("start_time", request.timeRange.fromToMillis.toString)
      .param("end_time", request.timeRange.toToMillisOrNow.toString)
      .param("q", request.query.encoded)
      .param("count", request.count.toString)
      .exec {
        HttpUtils.basicHandlersWith {
          case 200 => JsonFormat.andThan(JsonToModel(VkResponse.vkResponseReader))
        }
      }
      .body
  }

  private val NewsFeedPath = "/method/newsfeed.search"
}
