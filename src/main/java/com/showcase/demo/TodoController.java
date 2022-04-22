package com.showcase.demo;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
  private Mono<Void> save(@RequestBody Todo todo) {
    repository.save(todo);
    return Mono.empty();
  }
}
