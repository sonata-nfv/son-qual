package computerdatabase

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Recorder1 extends Simulation {

	val httpProtocol = http
		.baseURL("http://sp.int.alb.sonata-nfv.eu")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int.alb.sonata-nfv.eu/api/v2/services"

	val scn = scenario("RecordedSimulation1")
		.exec(http("request_0")
			.get("/api/v2/services"))

	setUp(scn.inject(atOnceUsers(10))).protocols(httpProtocol)
}

