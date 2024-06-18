package com.foodXpert.gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {
    // Routes configuration
    @Bean
    public RouterFunction<ServerResponse> productServiceRoute(){
        return GatewayRouterFunctions.route("product_service")
                .route(RequestPredicates.path("/api/v1/products/**"), HandlerFunctions.http("http://localhost:8080"))
                .route(RequestPredicates.path("/api/v1/categories/**"), HandlerFunctions.http("http://localhost:8080"))
                .build();
    }


    @Bean
    public RouterFunction<ServerResponse> userServiceRoute(){
        return GatewayRouterFunctions.route("user_service")
                .route(RequestPredicates.path("/api/v1/users/**"), HandlerFunctions.http("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> cartServiceRoute(){
        return GatewayRouterFunctions.route("cart_service")
                .route(RequestPredicates.path("/api/v1/carts/**"), HandlerFunctions.http("http://localhost:8082"))
                .build();
    }
}
