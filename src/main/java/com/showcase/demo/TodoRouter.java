package com.showcase.demo;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class TodoRouter {

  @Bean
  public RouterFunction<ServerResponse> route(TodoHandler todoHandler) {
    return RouterFunctions.route(
            GET("/todos/{id}").and(accept(MediaType.APPLICATION_JSON)), todoHandler::getById)
        .andRoute(POST("/todos").and(contentType(MediaType.APPLICATION_JSON)), todoHandler::save);
  }
}
