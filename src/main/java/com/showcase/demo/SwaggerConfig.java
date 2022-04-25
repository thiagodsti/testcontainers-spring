package com.showcase.demo;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public GroupedOpenApi todosGroupApi() {
    return GroupedOpenApi.builder().group("Todo").pathsToMatch("/todos/**").build();
  }
}
