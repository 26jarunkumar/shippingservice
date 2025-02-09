package com.arun.shippingservice.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ShippingRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("jetty:http://0.0.0.0:8082/shipping/generate") // Expose an HTTP endpoint
            .routeId("ShippingRoute")
            .process(exchange -> {
                // Generate a random device ID
                String deviceId = UUID.randomUUID().toString();
                exchange.getIn().setHeader("deviceId", deviceId);
            })
            .log("Generated device ID: ${header.deviceId}")
         .to("http://localhost:8080/order/update?bridgeEndpoint=true"); // Call Order Service to update

    	
		/*
		 * // Configure REST to use the servlet component restConfiguration()
		 * .component("servlet") // Use the servlet component .bindingMode("json") //
		 * Enable JSON binding .contextPath("/camel"); // Set the context path
		 * 
		 * // Define the REST endpoint rest("/shipping") // Base path for the REST
		 * endpoint .post("/generate") // Handle POST requests
		 * .to("shipping:generate").routeId("ShippingRoute");
		 * 
		 * // Process the order from("ShippingRoute") .marshal().json()
		 * .process(exchange -> { // Generate a random device ID String deviceId =
		 * UUID.randomUUID().toString(); exchange.getIn().setHeader("deviceId",
		 * deviceId); }) .log("Generated device ID: ${header.deviceId}"); //
		 * .to("http://localhost:8080/order/update?bridgeEndpoint=true");
		 */
    }
}