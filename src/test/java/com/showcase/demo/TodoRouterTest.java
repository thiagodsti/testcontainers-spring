package com.showcase.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@WebFluxTest(value = {TodoHandler.class, TodoRouter.class})
class TodoRouterTest {

  @Autowired private WebTestClient webClient;

  @MockBean private TodoRepository todoRepository;

  @Test
  public void shouldSaveTodo_andReturnLocation() {
    Todo todo = Todo.builder().title("test").description("test description").build();

    when(todoRepository.save(eq(todo))).thenReturn(1L);

    webClient
        .post()
        .uri("/todos")
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(todo))
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .valueMatches("Location", "/todos/1");
  }

  @Test
  public void shouldGetTodo() {
    Todo todo =
        Todo.builder()
            .title("test")
            .description("test description")
            .createdAt(Instant.now())
            .completedAt(Instant.now())
            .done(true)
            .build();

    when(todoRepository.findById(eq(1L))).thenReturn(Optional.of(todo));

    Todo found =
        webClient
            .get()
            .uri("/todos/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Todo.class)
            .returnResult()
            .getResponseBody();
    assertThat(found).isEqualTo(todo);
  }
}
