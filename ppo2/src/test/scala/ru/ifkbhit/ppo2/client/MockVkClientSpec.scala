package ru.ifkbhit.ppo2.client

import java.io.{ByteArrayInputStream, InputStream}

import org.junit.runner.RunWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.junit.JUnitRunner
import ru.ifkbhit.ppo2.client.http.HttpRequestProvider
import ru.ifkbhit.ppo2.util.http.HttpUtils.StringOps
import scalaj.http.{HttpRequest, HttpResponse}

@RunWith(classOf[JUnitRunner])
class MockVkClientSpec extends VkClientSpec with MockFactory {

  def mockedCall[T](request: ResponseRequest)(parser : (Int, Map[String, IndexedSeq[String]], InputStream) => T) : HttpResponse[T] = {
    val is = Option(request.response).map(new ByteArrayInputStream(_)).orNull
    HttpResponse(
      body = parser(request.resCode, Map.empty, is), code = request.resCode, headers = Map.empty
    )
  }

  override def getClient(requestResponse: ResponseRequest): VkClient = {
    val baseHttp: HttpRequest = mock[HttpRequest]


    val params: Seq[(String, String)] = Seq("v" -> Config.version, "access_token"-> Config.accessToken) ++ requestResponse.parameters

    params.foreach {
      case (k, v) =>
        (baseHttp.param _).expects(k, v.encoded)
          .returning(baseHttp)
          .once()
    }

    (baseHttp.exec _).expects(*).onCall(mockedCall(requestResponse) _)

    new DefaultVkClient(
      new HttpRequestProvider {
        override def getForUrl(url: String): HttpRequest = baseHttp
      },
      Config
    )

  }
}
