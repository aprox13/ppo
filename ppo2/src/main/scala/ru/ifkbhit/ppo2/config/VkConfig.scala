package ru.ifkbhit.ppo2.config

import ru.ifkbhit.ppo2.config.ConfigSupport.ConfigHelper
import ru.ifkbhit.ppo2.model.http.HttpHost

case class VkConfig(host: HttpHost, version: String, accessToken: String)

object VkConfig {
  def resolve(): VkConfig =
    new VkConfig(
      host = HttpHost(ps"vk.host", schema = ps"vk.schema"),
      version = ps"vk.version",
      accessToken = ps"vk.access.token"
    )
}
