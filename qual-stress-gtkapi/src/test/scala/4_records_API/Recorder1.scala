package recordsAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GetRecords1 extends Simulation {

    val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:32001")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:32001/api/v2/records"

	val scn = scenario("GetRecords1")
		.exec(http("records_1")
			.get("/api/v2/records"))

	setUp(scn.inject(atOnceUsers(1000))).protocols(httpProtocol)
}