package ru.ifkbhit.ppo2.client

import org.joda.time.DateTime
import org.scalatest.{Matchers, WordSpec}
import ru.ifkbhit.ppo2.config.VkConfig
import ru.ifkbhit.ppo2.model.TimeRange
import ru.ifkbhit.ppo2.model.http.{HttpHost, UnsupportedResponseCode}
import ru.ifkbhit.ppo2.model.request.NewsFeedRequest

trait VkClientSpec extends WordSpec with Matchers {

  protected val Config: VkConfig = VkConfig(HttpHost("", schema = ""), "version", "token")

  protected case class ResponseRequest(parameters: Seq[(String, String)], resCode: Int, response: Array[Byte])

  protected object ResponseRequest {
    private def params(request: NewsFeedRequest) = Seq(
      "q" -> request.query,
      "count" -> request.count.toString,
      "start_time" -> request.timeRange.fromToMillis.toString,
      "end_time" -> request.timeRange.toToMillisOrNow.toString
    )


    def apply(request: NewsFeedRequest, resCode: Int, response: String): ResponseRequest =
      new ResponseRequest(
        params(request),
        resCode,
        response.getBytes
      )

    def apply(request: NewsFeedRequest, code: Int): ResponseRequest =
      new ResponseRequest(
        params(request),
        code,
        null
      )
  }


  def getClient(requestResponse: ResponseRequest): VkClient

  private def newsFeedRequest: NewsFeedRequest =
    NewsFeedRequest("#кошки", TimeRange(DateTime.now().minusHours(2), Some(DateTime.now())), count = 0)


  "VkClient" should {

    "correctly return count" in {
      val request = newsFeedRequest
      val responseRequest = ResponseRequest(request, 200, """{"response": {"total_count": 1234}}""")

      val client = getClient(responseRequest)
      client.getNewsCountByTag(request).totalCount shouldBe 1234
    }

    "fail" when {
      "response is not 200" in {
        val nfr = newsFeedRequest

        val client = getClient(ResponseRequest(nfr, 300, "some ans"))
        a[UnsupportedResponseCode] should be thrownBy client.getNewsCountByTag(nfr)
      }

      "response is empty and code is 200" in {
        val nfr = newsFeedRequest
        val client = getClient(ResponseRequest(nfr, 200))
        a[RuntimeException] should be thrownBy client.getNewsCountByTag(nfr)
      }

      "response is empty and code is not 200" in {
        val nfr = newsFeedRequest
        val client = getClient(ResponseRequest(nfr, 200))
        a[RuntimeException] should be thrownBy client.getNewsCountByTag(nfr)
      }

      "json is bad" in {
        val request = newsFeedRequest
        val responseRequest = ResponseRequest(request, 200, """{"response1": {"total_count": 1234}}""")

        val client = getClient(responseRequest)
        a[RuntimeException] should be thrownBy client.getNewsCountByTag(request)
      }

    }
  }
}
