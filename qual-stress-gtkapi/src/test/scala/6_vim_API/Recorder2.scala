package vimAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GetVims2 extends Simulation {

import scala.util.parsing.json._

    val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:5700")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:5700/vim/compute-resources"

	val scn = scenario("GetVims2")
		.exec(http("vims_2")
			.get("/vim/compute-resources")
		)

	setUp(scn.inject(atOnceUsers(1000))).protocols(httpProtocol)
}
