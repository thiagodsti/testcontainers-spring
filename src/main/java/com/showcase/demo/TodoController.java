package com.showcase.demo;

import java.net.URI;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/todos")
@AllArgsConstructor
public class TodoController {

  private final TodoRepository repository;

  @GetMapping("/{id}")
  private Mono<Optional<Todo>> getById(@PathVariable Long id) {
    return Mono.fromCallable(() -> repository.findById(id)).subscribeOn(Schedulers.immediate());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  private Mono<ResponseEntity<Void>> save(
      @RequestBody Todo todo, UriComponentsBuilder componentsBuilder) {
    Long id = repository.save(todo);
    URI uri = componentsBuilder.path("/todos/{id}").buildAndExpand(id.toString()).toUri();
    return Mono.just(ResponseEntity.created(uri).build());
  }
}
