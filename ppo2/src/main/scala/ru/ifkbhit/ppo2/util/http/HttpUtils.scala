package ru.ifkbhit.ppo2.util.http

import java.io.InputStream
import java.net.URLEncoder

import ru.ifkbhit.ppo2.client.http.{ResponseFormat, StringResponseFormat}
import ru.ifkbhit.ppo2.model.http.UnsupportedResponseCode

import scala.util.Try

object HttpUtils {

  private val basicHandlers: PartialFunction[Int, String => Throwable] = {
    case 400 => msg => new RuntimeException(s"Server returns bad request. Message $msg")
    case 500 => msg => new RuntimeException(s"Internal server error. Message $msg")
    case code => UnsupportedResponseCode(code, _)
  }


  def basicHandlersWith[T](pf: PartialFunction[Int, ResponseFormat[InputStream, T]])(code: Int, mp: Map[String, IndexedSeq[String]], is: InputStream): T =
    if (pf.isDefinedAt(code)) {
      pf(code).format(is)
    } else {
      throw basicHandlers(code)(Try(StringResponseFormat.format(is)).getOrElse("EMPTY_CONTENT"))
    }

  implicit class StringOps(s: String) {
    def encoded: String = URLEncoder.encode(s, "UTF-8")
  }
}
