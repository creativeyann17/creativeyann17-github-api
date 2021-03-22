package com.creativeyann17.github.api.models;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Introspected
public abstract class WebSocketMessage {

  public enum Type {
    ARTICLE_VIEWS
  }

  protected Type type;

}
