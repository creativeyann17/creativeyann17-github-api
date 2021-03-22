package com.creativeyann17.github.api.controllers;

import com.creativeyann17.github.api.domain.View;
import com.creativeyann17.github.api.models.ArticleViewMessage;
import com.creativeyann17.github.api.models.ViewResponse;
import com.creativeyann17.github.api.repositories.ViewRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.websocket.WebSocketBroadcaster;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Controller("/views")
public class ViewController {

  @Inject
  private ViewRepository viewRepository;

  @Inject
  private WebSocketBroadcaster broadcaster;

  @Get("/{article}")
  public HttpResponse<ViewResponse> getViewArticle(@PathVariable String article) {
    final View view = viewRepository.findByArticle(article).orElse(View.builder().article(article).count(0).build());
    final ViewResponse response = new ViewResponse(view.getArticle(), view.getCount());
    return HttpResponse.ok(response);
  }

  @Post("/{article}")
  @Transactional
  public HttpResponse<ViewResponse> postViewArticle(@PathVariable String article) {
    final View view = viewRepository.findByArticle(article).orElse(View.builder().article(article).count(0).build());
    view.setCount(view.getCount() + 1);
    viewRepository.save(view);
    final ViewResponse response = new ViewResponse(view.getArticle(), view.getCount());
    broadcaster.broadcastAsync(new ArticleViewMessage(view.getArticle(), view.getCount()));
    return HttpResponse.ok(response);

  }
}
