package com.showcase.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoRouterTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void getTodo() {
    webTestClient
      // Create a GET request to test an endpoint
      .get().uri("/todos/1")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus().isOk()
      .expectBody(Todo.class).value(todo -> {
        assertThat(todo.getTitle()).isEqualTo("Test Demo");
    });
  }
}