package usersAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

import scala.util.Random

class UserRegistration2 extends Simulation {

    def userValue() = Random.nextInt(Integer.MAX_VALUE)
    def emailValue() = Random.alphanumeric.take(20).mkString + "@test.com"

	val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:5600")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:5600/api/v1/register/user"

	val scn = scenario("UserRegistration2")
		.exec(http("user_registration2")
			.post("/api/v1/register/user")
			.header("Content-Type", "application/json")     //.body(RawFileBody("test-user.json")).asJSON
			.body(StringBody(session =>
                    s"""
                       |{
                       |    "username": "${userValue()}",
                       |    "enabled": true,
                       |    "totp": false,
                       |    "emailVerified": false,
                       |    "firstName": "test",
                       |    "lastName": "test",
                       |    "email": "${emailValue()}",
                       |    "credentials": [
                       |        {
                       |            "type": "password",
                       |            "value": "${userValue()}"
                       |        }
                       |    ],
                       |    "requiredActions": [],
                       |    "federatedIdentities": [],
                       |    "attributes": {
                       |            "userType": ["developer","customer"]
                       |    },
                       |    "realmRoles": [],
                       |    "clientRoles": {},
                       |    "groups": []
                       |}
                    """.stripMargin)).asJSON
            .check(status.is(201)))

	setUp(scn.inject(nothingFor(5 seconds),rampUsers(100) over (10 seconds))).protocols(httpProtocol)
}