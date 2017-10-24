package usersAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

import scala.util.Random

class UserRegistration1 extends Simulation {

    def userValue() = Random.nextInt(Integer.MAX_VALUE)
    def emailValue() = Random.alphanumeric.take(20).mkString + "@test.com"

	val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:32001")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:32001/api/v2/users"

	val scn = scenario("UserRegistration1")
		.exec(http("user_registration1")
			.post("/api/v2/users")
			.header("Content-Type", "application/json")     //.body(RawFileBody("test-user.json")).asJSON
			.body(StringBody(session =>
                    s"""
                       |{
                       |    "username": "${userValue()}",
                       |    "firstName": "test",
                       |    "lastName": "test",
                       |    "email": "${emailValue()}",
                       |    "password": "${userValue()}",
                       |    "user_type": "developer"
                       |}
                    """.stripMargin)).asJSON
            .check(status.is(201)))

	setUp(scn.inject(nothingFor(5 seconds),rampUsers(100) over (10 seconds))).protocols(httpProtocol)
}
