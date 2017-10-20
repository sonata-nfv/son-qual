package packagesAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GetPackages3 extends Simulation {

    val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:4002")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:4002/catalogues/api/v2/packages"

    val testHeaders = Map("Content-Type" -> "application/json")

	val scn = scenario("GetPackages3")
		.exec(http("packages_3")
			.get("/catalogues/api/v2/packages")
			.headers(testHeaders)
			)

	setUp(scn.inject(
	        nothingFor(5 seconds),
	        //atOnceUsers(1000))
	        rampUsers(100) over (5 seconds)))
	    )
	.protocols(httpProtocol)
}
