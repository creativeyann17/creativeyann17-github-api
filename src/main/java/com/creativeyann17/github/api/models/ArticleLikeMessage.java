package com.creativeyann17.github.api.models;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Introspected
@Data
public class ArticleLikeMessage extends WebSocketMessage {

  private String article;
  private int count;

  public ArticleLikeMessage(String article, int count) {
    super(Type.ARTICLE_LIKES);
    this.article = article;
    this.count = count;
  }
}
