package com.developers.perspective.simulation


import com.developers.perspective.util._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import com.developers.perspective.scenarios.{GetAggregates}

class AggregateMicroserviceSimulation extends Simulation {

  val httpConf = http.baseUrl(Environemnt.baseURL)
                      .headers(Headers.commonHeader)

  val userMicroserviceScenarios = List(
    //PostAndGetUser.postAndGetUser.inject(atOnceUsers(1)),

    GetAggregates.getAggregates.inject(
      atOnceUsers(1),
      //rampUsers(100) over(1 seconds),
      //constantUsersPerSec(1000) during(15 seconds)
      rampUsersPerSec(1) to 200 during(120 seconds) // 6
//      rampUsersPerSec(10) to 20 during(10 minutes) randomized, // 7
      //splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy(10 seconds), // 8
      //splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy atOnceUsers(30), // 9
      //heavisideUsers(1000) over(20 seconds) // 10
    ),

  )

  setUp(userMicroserviceScenarios)
    .protocols(httpConf)
    .maxDuration(60 minutes)
    .assertions(
      global.responseTime.max.lt(Environemnt.maxResponseTime.toInt)
    )
}