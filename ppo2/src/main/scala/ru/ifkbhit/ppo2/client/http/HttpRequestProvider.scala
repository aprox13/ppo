package ru.ifkbhit.ppo2.client.http

import scalaj.http.{Http, HttpRequest}

class HttpRequestProvider {
  def getForUrl(url: String): HttpRequest =
    Http(url).copy(headers = Seq())
}

