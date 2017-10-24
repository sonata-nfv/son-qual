package microservicesAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

import scala.util.Random

class MicroserviceRegistration1 extends Simulation {

    def clientValue() = Random.nextInt(Integer.MAX_VALUE)
    def redirectValue() = Random.alphanumeric.take(20).mkString

	val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:32001")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:32001/api/v2/micro-services"

	val scn = scenario("MicroserviceRegistration1")
		.exec(http("microservice_registration1")
			.post("/api/v2/micro-services")
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

	setUp(scn.inject(nothingFor(5 seconds),rampUsers(25) over (10 seconds))).protocols(httpProtocol)
}
