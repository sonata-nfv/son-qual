package recordsAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GetRecords2 extends Simulation {

    val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:5800")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:5800/services"

	val scn = scenario("GetRecords2")
		.exec(http("records_2")
			.get("/services"))

	setUp(scn.inject(nothingFor(5 seconds),atOnceUsers(1000))).protocols(httpProtocol)
}