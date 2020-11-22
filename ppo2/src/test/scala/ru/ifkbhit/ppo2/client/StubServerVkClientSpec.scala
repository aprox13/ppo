package ru.ifkbhit.ppo2.client

import com.xebialabs.restito.builder.stub.StubHttp.whenHttp
import com.xebialabs.restito.semantics.Condition
import com.xebialabs.restito.semantics.Condition._
import com.xebialabs.restito.semantics.Action._
import com.xebialabs.restito.server.StubServer
import org.glassfish.grizzly.http.Method
import org.glassfish.grizzly.http.util.HttpStatus
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.scalatest.junit.JUnitRunner
import ru.ifkbhit.ppo2.client.http.HttpRequestProvider
import ru.ifkbhit.ppo2.model.http.HttpHost
import ru.ifkbhit.ppo2.util.http.HttpUtils.StringOps

@RunWith(classOf[JUnitRunner])
class StubServerVkClientSpec extends VkClientSpec with BeforeAndAfter {

  private var server: StubServer = _
  private val StubPort: Int = 8762


  before {
    server = new StubServer(StubPort).run()
    server should not be null
  }

  after {
    server.stop()
  }

  private def toCondition(param: (String, String)): Condition = parameter(param._1, param._2.encoded)

  override def getClient(requestResponse: ResponseRequest): VkClient = {
    val config = Config.copy(host = HttpHost("localhost", Some(StubPort), schema = "http"))

    val params: Seq[(String, String)] =
      Seq("v" -> config.version, "access_token"-> config.accessToken) ++
        requestResponse.parameters
    val actions =
      Seq(status(HttpStatus.getHttpStatus(requestResponse.resCode))) ++
        (if (requestResponse.response != null) Seq(bytesContent(requestResponse.response)) else Seq())

    val input = Seq(
      method(Method.GET),
      startsWithUri("/method/newsfeed.search"),
    ) ++ params.map(toCondition)

    whenHttp(server)
      .`match`(input: _*)
      .`then`(actions: _*)

    val httpRequestProvider = new HttpRequestProvider

    new DefaultVkClient(httpRequestProvider, config)
  }
}
