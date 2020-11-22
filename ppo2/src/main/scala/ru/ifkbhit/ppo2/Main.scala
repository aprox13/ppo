package ru.ifkbhit.ppo2

import org.joda.time.DateTime
import ru.ifkbhit.ppo2.client.DefaultVkClient
import ru.ifkbhit.ppo2.client.http.HttpRequestProvider
import ru.ifkbhit.ppo2.config.VkConfig

object Main {

  def main(args: Array[String]): Unit = {
    val client = new DefaultVkClient(new HttpRequestProvider, VkConfig.resolve())
    val stat = new DefaultNewsStatistic(client)

    println(
      stat.getCountsInDay("#котики", DateTime.now())
    )
  }
}
