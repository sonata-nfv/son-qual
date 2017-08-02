package computerdatabase

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Recorder2 extends Simulation {

	val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:5600")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:5600/api/v1/public-key"

	val scn = scenario("RecordedSimulation2")
		.exec(http("get_public_key")
			.get("/api/v1/public-key"))

	setUp(scn.inject(atOnceUsers(100))).protocols(httpProtocol)
}

