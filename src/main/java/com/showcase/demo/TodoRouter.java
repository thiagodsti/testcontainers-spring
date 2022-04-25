package com.showcase.demo;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class TodoRouter {

  @Bean
  @RouterOperations({
    @RouterOperation(
        path = "/todos",
        produces = {MediaType.APPLICATION_JSON_VALUE},
        method = RequestMethod.POST,
        beanClass = TodoHandler.class,
        beanMethod = "save",
        operation =
            @Operation(
                operationId = "saveTodo",
                responses = {
                  @ApiResponse(responseCode = "201", description = "successful operation"),
                  @ApiResponse(responseCode = "400", description = "Bad Request")
                },
                requestBody =
                    @RequestBody(
                        content = @Content(schema = @Schema(implementation = TodoSave.class))))),
    @RouterOperation(
        path = "/todos/{todoId}",
        produces = {MediaType.APPLICATION_JSON_VALUE},
        beanClass = TodoHandler.class,
        method = RequestMethod.GET,
        beanMethod = "getById",
        operation =
            @Operation(
                operationId = "getTodo",
                responses = {
                  @ApiResponse(
                      responseCode = "200",
                      description = "successful operation",
                      content = @Content(schema = @Schema(implementation = Todo.class))),
                  @ApiResponse(responseCode = "404", description = "Todo not found")
                },
                parameters = {@Parameter(in = ParameterIn.PATH, name = "todoId")})),
    @RouterOperation(
        path = "/todos/{todoId}",
        beanClass = TodoHandler.class,
        method = RequestMethod.DELETE,
        beanMethod = "remove",
        operation =
            @Operation(
                operationId = "deleteTodo",
                responses = {
                  @ApiResponse(responseCode = "200", description = "successful operation"),
                },
                parameters = {@Parameter(in = ParameterIn.PATH, name = "todoId")})),
    @RouterOperation(
        path = "/todos/{todoId}",
        produces = {MediaType.APPLICATION_JSON_VALUE},
        method = RequestMethod.PUT,
        beanClass = TodoHandler.class,
        beanMethod = "update",
        operation =
            @Operation(
                operationId = "updateTodo",
                responses = {
                  @ApiResponse(responseCode = "200", description = "successful operation"),
                  @ApiResponse(responseCode = "400", description = "Bad Request"),
                  @ApiResponse(responseCode = "404", description = "Todo not found")
                },
                parameters = {@Parameter(in = ParameterIn.PATH, name = "todoId")},
                requestBody =
                    @RequestBody(
                        content = @Content(schema = @Schema(implementation = TodoUpdate.class)))))
  })
  public RouterFunction<ServerResponse> route(TodoHandler todoHandler) {
    return RouterFunctions.route(
            GET("/todos/{id}").and(accept(MediaType.APPLICATION_JSON)), todoHandler::getById)
        .andRoute(POST("/todos").and(contentType(MediaType.APPLICATION_JSON)), todoHandler::save)
        .andRoute(DELETE("/todos/{id}"), todoHandler::remove)
        .andRoute(
            PUT("/todos/{id}").and(contentType(MediaType.APPLICATION_JSON)), todoHandler::update);
  }
}
