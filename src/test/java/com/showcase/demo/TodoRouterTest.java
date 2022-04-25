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
  public void saveTodo_andReturnLocation() {
    var todo = TodoSave.builder().title("test").description("test description").build();

    when(todoRepository.save(eq(todo))).thenReturn(Todo.builder().id(1L).build());

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
  public void getTodo_shouldBeSuccessful() {
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

  @Test
  public void getTodo_receive404_whenTodoNotFound() {
    when(todoRepository.findById(eq(1L))).thenReturn(Optional.empty());

    webClient
        .get()
        .uri("/todos/1")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  public void deleteTodo_shouldBeSuccessful() {
    webClient.delete().uri("/todos/1").exchange().expectStatus().isNoContent().expectHeader();
  }

  @Test
  public void updateTodo_receive404_whenNotFound() {
    TodoUpdate todoUpdate =
        TodoUpdate.builder().title("test").description("test description").build();
    when(todoRepository.findById(eq(1L))).thenReturn(Optional.empty());

    webClient
        .put()
        .uri("/todos/1")
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(todoUpdate))
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  public void updateTodo_shouldBeSuccessful() {
    TodoUpdate todoUpdate =
        TodoUpdate.builder().title("test").description("test description").build();
    Todo todo = Todo.builder().id(1L).build();
    when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
    when(todoRepository.update(todoUpdate, 1L)).thenReturn(todo);

    webClient
        .put()
        .uri("/todos/1")
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(todoUpdate))
        .exchange()
        .expectStatus()
        .isOk();
  }
}
