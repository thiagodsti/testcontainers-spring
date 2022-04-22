package com.showcase.demo;

import java.util.Optional;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository {

  @SqlUpdate(
      "INSERT INTO todo(title, description, created_at) VALUES (:title, :description, NOW())")
  @GetGeneratedKeys
  Long save(@BindMethods final Todo todo);

  @SqlQuery("SELECT * FROM todo WHERE id = :id")
  Optional<Todo> findById(@Bind Long id);
}
