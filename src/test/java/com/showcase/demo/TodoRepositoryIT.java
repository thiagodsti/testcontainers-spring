package com.showcase.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class TodoRepositoryIT {

  @Autowired private TodoRepository repository;

  @Autowired private JdbcTemplate jdbcTemplate;

  @AfterEach
  public void cleanUp() {
    jdbcTemplate.execute("DELETE FROM todo");
  }

  @Test
  void insertTodo() {
    var todo = TodoSave.builder().title("Test").description("Description").build();
    var saved = repository.save(todo);
    assertThat(saved.id()).isNotNull();
  }

  @Test
  void insertAndDeleteTodo() {
    var todo = TodoSave.builder().title("Test").description("Description").build();
    var saved = repository.save(todo);
    repository.deleteById(saved.id());
    assertThat(repository.findById(saved.id())).isEmpty();
  }

  @Test
  void insertAndUpdateTodo() {
    var todo = TodoSave.builder().title("Test").description("Description").build();
    var id = repository.save(todo).id();
    var updated = repository.update(
        TodoUpdate.builder().title("newTest").description("newDescription").build(), id);
    var found = repository.findById(updated.id()).get();
    assertThat(found.title()).isEqualTo("newTest");
    assertThat(found.description()).isEqualTo("newDescription");
  }
}
