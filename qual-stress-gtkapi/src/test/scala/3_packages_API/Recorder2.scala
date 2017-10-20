package packagesAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GetPackages2 extends Simulation {

    val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:5100")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:5100/packages"

	val scn = scenario("GetPackages2")
		.exec(http("packages_2")
			.get("/packages"))

	//setUp(scn.inject(nothingFor(5 seconds),atOnceUsers(1000))).protocols(httpProtocol)
	setUp(scn.inject(nothingFor(5 seconds),rampUsers(100) over (5 seconds))).protocols(httpProtocol)
}