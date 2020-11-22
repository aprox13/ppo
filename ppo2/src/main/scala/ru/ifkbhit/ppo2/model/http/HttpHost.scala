package ru.ifkbhit.ppo2.model.http

case class HttpHost(host: String, port: Option[Int] = None, schema: String) {

  def toUrl: String = {
    val portPart = port.map(":" + _).getOrElse("")
    s"$schema://$host$portPart"
  }

  def withPath(path: String): String = {
    require(path.startsWith("/"), s"Path '$path' must starts with '/'")
    s"$toUrl$path"
  }
}
