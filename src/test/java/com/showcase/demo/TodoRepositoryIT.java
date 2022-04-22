package com.showcase.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@Testcontainers
@ActiveProfiles("test")
public class TodoRepositoryIT {

  @Autowired private TodoRepository repository;

  @Test
  void insertTodo() {
    Todo todo = Todo.builder().title("Test").description("Description").build();
    Long id = repository.save(todo);
    assertThat(id).isEqualTo(1);
  }
}
