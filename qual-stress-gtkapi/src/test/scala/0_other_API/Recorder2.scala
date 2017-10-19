package otherAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UMPublicKey extends Simulation {

	val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:5600")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:5600/api/v1/public-key"

	val scn = scenario("UMPublicKey")
		.exec(http("um_get_public_key")
			.get("/api/v1/public-key"))

	setUp(scn.inject(nothingFor(5 seconds),atOnceUsers(100))).protocols(httpProtocol)
}

