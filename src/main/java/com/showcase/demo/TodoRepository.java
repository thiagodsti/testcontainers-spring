package com.showcase.demo;

import java.util.Optional;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository {

  @SqlUpdate(
      "INSERT INTO todo(title, description, done, completed_at, created_at) VALUES (:title, :description, :done, :completedAt, NOW())")
  @GetGeneratedKeys
  Todo save(@BindMethods final TodoSave todo);

  @SqlQuery("SELECT * FROM todo WHERE id = :id")
  Optional<Todo> findById(@Bind Long id);

  @SqlUpdate("DELETE FROM todo WHERE id = :id")
  void deleteById(@Bind Long id);

  @SqlUpdate(
      """
      UPDATE todo SET title = :title,
        description = :description,
        done = :done, completed_at = :completedAt
      WHERE id = :id""")
  @GetGeneratedKeys
  Todo update(@BindMethods final TodoUpdate todoUpdate, @Bind Long id);
}
