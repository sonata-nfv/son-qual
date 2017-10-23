package functionsAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GetFunctions2 extends Simulation {

    val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:5500")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:5500/functions"

	val scn = scenario("GetFunctions2")
		.exec(http("functions_2")
			.get("/functions"))

    setUp(scn.inject(
            nothingFor(5 seconds),
            //atOnceUsers(1000))
            rampUsers(100) over (5 seconds))
        )
    .protocols(httpProtocol)
}