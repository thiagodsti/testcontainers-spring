package com.showcase.demo;

import java.time.Instant;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Todo {

  String title;
  String description;
  boolean done;
  Instant createdAt;
  Instant completedAt;
}
