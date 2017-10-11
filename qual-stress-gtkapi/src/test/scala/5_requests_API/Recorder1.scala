package requestsAPI

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GetRequests1 extends Simulation {

    val httpProtocol = http
		.baseURL("http://sp.int3.sonata-nfv.eu:32001")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("curl/7.35.0")

    val uri1 = "http://sp.int3.sonata-nfv.eu:32001/api/v2/requests"

    //val headers_2 = Map("Authorization" -> "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJJaHp3aUdVUERVTjVKSG9wdUhBcGhtbVhSbE14QzIxcTRoekI3cllEZ2VNIn0.eyJqdGkiOiI2ZGEyN2NkYS00NzgzLTRlMjMtOGExMi1mNTM3NDJhNzhkOTAiLCJleHAiOjE1MDc2MzM1OTEsIm5iZiI6MCwiaWF0IjoxNTA3NjMyMzkxLCJpc3MiOiJodHRwOi8vc29uLWtleWNsb2FrOjU2MDEvYXV0aC9yZWFsbXMvc29uYXRhIiwiYXVkIjoiYWRhcHRlciIsInN1YiI6IjkyY2RlY2UzLTI4OTktNDMyYS05NzgyLWQyMzM2MzE4NmQwMSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImFkYXB0ZXIiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiI0MmRhZDk0OC1jMjZkLTRiODUtOGQ2NC0xYjI2MDRhZTEyYWUiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9zb24tZ3RrdXNyOjU2MDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRldmVsb3BlciIsInVtYV9hdXRob3JpemF0aW9uIiwiY3VzdG9tZXIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJuYW1lIjoiRGVtbyBVc2VyIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiZGVtbyIsImdpdmVuX25hbWUiOiJEZW1vIiwiZmFtaWx5X25hbWUiOiJVc2VyIiwiZW1haWwiOiJkZW1vLnVzZXJAZW1haWwuY29tIn0.LNyTVEZmm7-zvUFVxdp3moojgXcE7W-8by6gnEl2Fr6mHHHEdnQVaPqk6Dzc5949Ivuh8yJfR6UkC8zC1_ShjioAmVYe3th6ixkjywfJGgrOKYXoolmEwh-PZS0T-aC8fnqEFPmGu6Q5NjkgO05gtK_9MVOk_J3FHiND7hj2uEVevBCr8tg_FlCz6uI6dLaFjOxhochYPFxi55HEQpD7-CzKzbVB0dDgnkxjI3fCa0dkQwa-0OPoyn-Qd4QaY3mhAddL69B08cYIcZFXGSyYAsmaTZr674peK-nokTqakziPK_PFKZEmvXG9Oc9hr2lvpwTV6KQU2CWdSfoY86rYfg")

	val scn = scenario("GetRequests1")
        .exec(
             http("requesting_access_token")
                .post("http://sp.int3.sonata-nfv.eu:32001/api/v2/sessions")
                 .headers(Map(
                     "Accept" -> "application/json, */*; q=0.01",
                     "Content-Type" -> "application/json"
                     )
                 )
                 //.body(RawFileBody("test-credentials.txt"))
                 .body(StringBody(session =>
                                     s"""
                                        |{
                                        |    "username":"user01",
                                        |    "password":"1234"
                                        |}
                                       """.stripMargin)).asJSON
                 .check(status.is(200))
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
        .exec(http("requests_1")
            .get("/api/v2/requests")
            .headers(Map(
                "Authorization" -> "bearer ${accessToken}"
            )
        )
        )

	setUp(scn.inject(atOnceUsers(1000))).protocols(httpProtocol)
}