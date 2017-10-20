package recordsAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GetRecords3 extends Simulation {

    val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:4002")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:4002/records/nsr/ns-instances"

    val testHeaders = Map("Content-Type" -> "application/json")

	val scn = scenario("GetRecords3")
		.exec(http("records_3")
			.get("/records/nsr/ns-instances")
			.headers(testHeaders)
			)

	setUp(scn.inject(
	        nothingFor(5 seconds),
	        //atOnceUsers(1000))
	        rampUsers(100) over (5 seconds)))
	    )
	.protocols(httpProtocol)
}
