package ru.ifkbhit.ppo2.model.response

import play.api.libs.json.{Json, OFormat}

case class VkResponse(response: VkCountResponse)

object VkResponse {
  implicit val vkResponseReader: OFormat[VkResponse] = Json.format[VkResponse]
}
