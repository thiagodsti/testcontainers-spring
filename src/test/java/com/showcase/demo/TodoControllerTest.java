package com.showcase.demo;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@WebFluxTest(TodoController.class)
class TodoControllerTest {


  @Autowired
  private WebTestClient webClient;

  @MockBean
  private TodoRepository todoRepository;

  @Test
  public void shouldSaveTodo() {
    Todo todo =
        Todo.builder()
            .title("test")
            .description("test description")
            .build();

    when(todoRepository.save(eq(todo))).thenReturn(1L);

    webClient
        .post().uri("/todos")
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(todo))
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader().valueMatches("Location", "/todos/1");
  }
}