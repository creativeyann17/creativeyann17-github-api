package com.creativeyann17.github.api.controllers;

import com.creativeyann17.github.api.domain.Like;
import com.creativeyann17.github.api.mappers.LikeMapper;
import com.creativeyann17.github.api.models.ArticleLikeMessage;
import com.creativeyann17.github.api.models.LikeResponse;
import com.creativeyann17.github.api.repositories.LikeRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.websocket.WebSocketBroadcaster;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Controller("/likes/{article}")
public class LikeController {

  @Inject
  private LikeRepository likeRepository;

  @Inject
  private LikeMapper likeMapper;

  @Inject
  private WebSocketBroadcaster broadcaster;

  @Get
  public HttpResponse<LikeResponse> getLikeArticle(@PathVariable String article) {
    final Like like = likeRepository.findByArticle(article).orElse(likeMapper.emptyLike(article));
    final LikeResponse likeResponse = likeMapper.mapToLikeResponse(like);
    return HttpResponse.ok(likeResponse);
  }

  @Post
  @Transactional
  public HttpResponse<LikeResponse> postLikeArticle(@PathVariable String article) {
    final Like like = likeRepository.findByArticle(article).orElse(likeMapper.emptyLike(article));
    likeRepository.save(likeMapper.incCountOfLikes(like));
    final LikeResponse likeResponse = likeMapper.mapToLikeResponse(like);
    broadcaster.broadcastAsync(new ArticleLikeMessage(like.getArticle(), like.getCount()));
    return HttpResponse.ok(likeResponse);
  }

}
