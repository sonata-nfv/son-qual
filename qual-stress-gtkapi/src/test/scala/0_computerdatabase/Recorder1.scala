package computerdatabase

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Recorder1 extends Simulation {

	val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:5600")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:5600/admin/log"

	val scn = scenario("RecordedSimulation1")
		.exec(http("request_0")
			.get("/admin/log"))

	setUp(scn.inject(atOnceUsers(1000))).protocols(httpProtocol)
}

