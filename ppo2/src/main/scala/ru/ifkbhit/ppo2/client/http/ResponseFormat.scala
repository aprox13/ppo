package ru.ifkbhit.ppo2.client.http

import java.io.{BufferedReader, InputStream, InputStreamReader}
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors

import play.api.libs.json.{JsValue, Json, Reads}
import ru.ifkbhit.ppo2.model.http.BadContentException

import scala.util.Try


trait ResponseFormat[F, T] {
  def format(f: F): T

  def andThan[K](next: ResponseFormat[T, K]): ResponseFormat[F, K] =
    (f: F) => next.format(format(f))
}

object StringResponseFormat extends ResponseFormat[InputStream, String] {
  override def format(f: InputStream): String =
    new BufferedReader(
      Option(f).map(new InputStreamReader(_, StandardCharsets.UTF_8)).getOrElse(throw BadContentException)
    ).lines().collect(Collectors.joining("\n"))
}

object JsonFormat extends ResponseFormat[InputStream, JsValue] {
  override def format(f: InputStream): JsValue =
    Try { Option(f).map(Json.parse).get }.getOrElse(throw BadContentException)
}

case class JsonToModel[T](reader: Reads[T]) extends ResponseFormat[JsValue, T] {
  override def format(f: JsValue): T =
    Try { Json.fromJson(f)(reader).get }
      .getOrElse(throw new RuntimeException(s"Couldn't convert json to model. Value: ${f.toString()}"))
}

