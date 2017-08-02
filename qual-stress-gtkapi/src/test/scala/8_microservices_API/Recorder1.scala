package microservicesAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

import scala.util.Random

class MicroserviceRegistration extends Simulation {

    def clientValue() = Random.nextInt(Integer.MAX_VALUE)
    def redirectValue() = Random.alphanumeric.take(20).mkString

	val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:5600")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:5600/api/v1/register/service"

	val scn = scenario("MicroserviceRegistration")
		.exec(http("microservice_registration")
			.post("/api/v1/register/service")
			.header("Content-Type", "application/json")
			.body(StringBody(session =>
                    s"""
                       |{
                       |    "clientId": "${clientValue()}",
                       |    "surrogateAuthRequired": false,
                       |    "enabled": true,
                       |    "clientAuthenticatorType": "client-secret",
                       |    "secret": "1234",
                       |    "redirectUris": [
                       |        "/auth/${redirectValue()}"
                       |    ],
                       |    "webOrigins": [],
                       |    "notBefore": 0,
                       |    "bearerOnly": false,
                       |    "clientRoles": {},
                       |    "consentRequired": false,
                       |    "standardFlowEnabled": true,
                       |    "implicitFlowEnabled": false,
                       |    "directAccessGrantsEnabled": true,
                       |    "serviceAccountsEnabled": true,
                       |    "publicClient": false,
                       |    "frontchannelLogout": false,
                       |    "protocol": "openid-connect",
                       |    "fullScopeAllowed": false
                       |}
                      """.stripMargin)).asJSON
            .check(status.is(201)))

	setUp(scn.inject(rampUsers(25) over (10 seconds))).protocols(httpProtocol)
}
