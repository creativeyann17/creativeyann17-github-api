package com.creativeyann17.github.api.mappers;

import com.creativeyann17.github.api.domain.Like;
import com.creativeyann17.github.api.models.LikeResponse;

import javax.inject.Singleton;

@Singleton
public class LikeMapper {

  public Like emptyLike(final String article) {
    return Like.builder().article(article).count(0).build();
  }

  public LikeResponse mapToLikeResponse(final Like like) {
    return new LikeResponse(like.getArticle(), like.getCount());
  }

  public Like incCountOfLikes(final Like like) {
    like.setCount(like.getCount() + 1);
    return like;
  }
}
