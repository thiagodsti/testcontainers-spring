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
        .bodyToMono(TodoSave.class)
        .map(repository::save)
        .map(todo -> componentsBuilder.path("/{id}").buildAndExpand(todo.id()).toUri())
        .flatMap(uri -> ServerResponse.created(uri).build());
  }

  @NonNull
  public Mono<ServerResponse> getById(ServerRequest request) {
    Optional<Todo> todo = repository.findById(Long.parseLong(request.pathVariable("id")));
    if (todo.isEmpty()) {
      return ServerResponse.notFound().build();
    }
    return ServerResponse.ok().bodyValue(todo);
  }

  @NonNull
  public Mono<ServerResponse> remove(ServerRequest request) {
    repository.deleteById(Long.parseLong(request.pathVariable("id")));
    return ServerResponse.noContent().build();
  }

  public Mono<ServerResponse> update(ServerRequest request) {
    long id = Long.parseLong(request.pathVariable("id"));
    Optional<Todo> todoOpt = repository.findById(id);
    if (todoOpt.isEmpty()) {
      return ServerResponse.notFound().build();
    }

   return request
       .bodyToMono(TodoUpdate.class)
       .map(todo -> repository.update(todo, id))
       .flatMap(todo -> ServerResponse.ok().build());
  }
}
