package com.creativeyann17.github.api.models;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Introspected
@Data
public class ArticleViewMessage extends WebSocketMessage {

  private String article;
  private int count;

  public ArticleViewMessage(String article, int count) {
    super(Type.ARTICLE_VIEWS);
    this.article = article;
    this.count = count;
  }
}
