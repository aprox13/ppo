package ru.ifkbhit.ppo2.model.response

import play.api.libs.json.JsonConfiguration.Aux
import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration, OFormat}

case class VkCountResponse(totalCount: Int)

object VkCountResponse {
  implicit val config: Aux[Json.MacroOptions] = JsonConfiguration(SnakeCase)
  implicit val vkCountResponseReader: OFormat[VkCountResponse] = Json.format[VkCountResponse]
}
