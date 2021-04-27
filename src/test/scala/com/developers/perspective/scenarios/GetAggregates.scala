package com.developers.perspective.scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object GetAggregates {


  //http://localhost:8080/test?track=NL,CN&pricing=109347263,123456891&shipment=109347263,123456891

  val aggregationInput = csv("feeder.csv").random
  val getAggregates = scenario("get aggreagtes")
    .feed(aggregationInput)
    .exec(
      http("get aggreagtes")
        .get("/aggregation")
        .queryParam("pricing", "${pricing}")
        .queryParam("track", "${track}")
        .queryParam("shipment", "${shipment}")
        .check(status.is(200))
    )
}
