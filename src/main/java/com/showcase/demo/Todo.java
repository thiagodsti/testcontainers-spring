package com.showcase.demo;

import java.time.Instant;
import lombok.Builder;

@Builder(toBuilder = true)
public record Todo(Long id, String title, String description, boolean done, Instant createdAt,
                   Instant completedAt) { }