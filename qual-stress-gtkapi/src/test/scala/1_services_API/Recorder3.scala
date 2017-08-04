package servicesAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GetServices3 extends Simulation {

    val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:4002")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:4002/catalogues/api/v2/network-services"

	val scn = scenario("GetServices3")
		.exec(http("services_3")
			.get("/catalogues/api/v2/network-services"))

	setUp(scn.inject(
	        nothingFor(4 seconds),
	        atOnceUsers(1000))
	    )
	.protocols(httpProtocol)
}
