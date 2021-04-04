package com.creativeyann17.github.api.domain;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Introspected
@Entity
@Table(
    name = "likes",
    indexes = {@Index(name = "likes_article_index", columnList = "article", unique = true)})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like {
  @Id
  @GeneratedValue
  private long id;

  @Column(name = "article", nullable = false)
  private String article;

  @Column(name = "count", nullable = false)
  private int count;

}
