package com.showcase.demo;

import java.time.Instant;
import lombok.Builder;

@Builder(toBuilder = true)
public record TodoUpdate(String title, String description, boolean done, Instant completedAt) {}
