package com.showcase.demo;

import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
public record TodoHandler(TodoRepository repository) {

  @NonNull
  public Mono<ServerResponse> save(ServerRequest request) {
    UriComponentsBuilder componentsBuilder =
        UriComponentsBuilder.fromPath(request.requestPath().value());
    return request
        .bodyToMono(Todo.class)
        .map(repository::save)
        .map(id -> componentsBuilder.path("/{id}").buildAndExpand(id).toUri())
        .flatMap(uri -> ServerResponse.created(uri).build());
  }

  @NonNull
  public Mono<ServerResponse> getById(ServerRequest request) {
    Optional<Todo> todo = repository.findById(Long.parseLong(request.pathVariable("id")));
    return ServerResponse.ok().bodyValue(todo);
  }
}
