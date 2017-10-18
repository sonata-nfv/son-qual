package requestsAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GetRequests2 extends Simulation {

    val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:5300")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:5300/requests"

	val scn = scenario("GetRequests2")
		.exec(http("requests_2")
			.get("/requests"))

	setUp(scn.inject(nothingFor(5 seconds),atOnceUsers(1000))).protocols(httpProtocol)
}