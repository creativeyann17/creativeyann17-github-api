package com.creativeyann17.github.api.models;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;

@Introspected
@Data
@AllArgsConstructor
public class ViewResponse {
  private String article;
  private int count;
}
