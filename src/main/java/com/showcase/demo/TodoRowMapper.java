package com.showcase.demo;

import com.showcase.demo.Todo.TodoBuilder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class TodoRowMapper implements RowMapper<Todo> {

  @Override
  public Todo map(ResultSet rs, StatementContext ctx) throws SQLException {
    TodoBuilder builder = Todo.builder();
    Timestamp completedAtTs = rs.getTimestamp("completed_at");
    if (completedAtTs != null) {
      builder.completedAt(completedAtTs.toInstant());
    }
    return builder
        .id(rs.getLong("id"))
        .title(rs.getString("title"))
        .description(rs.getString("description"))
        .createdAt(rs.getTimestamp("created_at").toInstant())
        .done(rs.getBoolean("done"))
        .build();
  }
}
