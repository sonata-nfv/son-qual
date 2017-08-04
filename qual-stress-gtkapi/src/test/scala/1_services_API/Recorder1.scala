package servicesAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

import scala.util.parsing.json._

class GetServices1 extends Simulation {

    val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:32001")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

	val loginRequest = tryMax(1) {
                exec(
                    http("requesting_access_token")
                        .post("http://sp.int3.sonata-nfv.eu:32001/api/v2/sessions")
                        .headers(Map(
                            "Accept" -> "application/json, */*; q=0.01",
                            "Content-Type" -> "application/json; charset=UTF-8"
                            )
                        )
                        .body(StringBody(
                            Util.jsonFromMap(Map(
                                "username" -> "user01",
                                "password" -> "1234"
                            ))
                        ))
                        .check(status.is(200))
                        //.check(bodyString.saveAs("login_response"))
                        .check(
                              jsonPath("$.access_token").saveAs("access_token")
                            )
                )
                .exec(
                    session => {
                    val accessToken = session.get("access_token").asOption[String]
                    println(accessToken.getOrElse("COULD NOT FIND TOKEN"))
                    session
                })

    }.exitHereIfFailed

    val uri1 = "http://sp.int3.sonata-nfv.eu:32001/api/v2/services"

	val scn = scenario("GetServices1")
	    .exec(loginRequest)
		.exec(
		    http("services_1")
			.get("/api/v2/services")
			.headers(Map(
                "Authorization" -> "bearer ${accessToken}"
                )
			)

	setUp
	    (scn.inject(
	        nothingFor(4 seconds),
	        atOnceUsers(1000))
	    )
	.protocols(httpProtocol)
}

class GetServices2 extends Simulation {

    val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:5300")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:5300/services"

	val scn = scenario("GetServices2")
		.exec(http("services_2")
			.get("/services"))

	setUp
	    (scn.inject(
	        nothingFor(4 seconds),
	        atOnceUsers(1000))
	    )
	.protocols(httpProtocol)
}

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

	setUp
	    (scn.inject(
	        nothingFor(4 seconds),
	        atOnceUsers(1000))
	    )
	.protocols(httpProtocol)
}
