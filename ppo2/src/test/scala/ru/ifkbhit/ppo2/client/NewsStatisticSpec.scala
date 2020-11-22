package ru.ifkbhit.ppo2.client

import org.joda.time.DateTime
import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}
import ru.ifkbhit.ppo2.DefaultNewsStatistic
import ru.ifkbhit.ppo2.model.response.VkCountResponse

@RunWith(classOf[JUnitRunner])
class NewsStatisticSpec extends WordSpec with Matchers with MockFactory {

  "NewsStatistic" should {
    "correctly work" in {
      val client = mock[VkClient]

      (client.getNewsCountByTag _).expects(*).returning(VkCountResponse(1))
        .repeat(24)

      val stat = new DefaultNewsStatistic(client)
      val expected: Seq[Int] =  (0 to 23).map(_ => 1)
      val actual = stat.getCountsInDay("sss", DateTime.now())

      actual should contain theSameElementsAs expected
    }

    "fail" when {
      "comes empty query" in {
        val client = mock[VkClient]
        val stat = new DefaultNewsStatistic(client)

        a[IllegalArgumentException] should be thrownBy stat.getCountsInDay("", DateTime.now())
      }
    }
  }
}
