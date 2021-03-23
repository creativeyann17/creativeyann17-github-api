package com.creativeyann17.github.api.controllers;

import com.creativeyann17.github.api.domain.View;
import com.creativeyann17.github.api.mappers.ViewMapper;
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

@Controller("/views/{article}")
public class ViewController {

  @Inject
  private ViewRepository viewRepository;

  @Inject
  private ViewMapper viewMapper;

  @Inject
  private WebSocketBroadcaster broadcaster;

  @Get
  public HttpResponse<ViewResponse> getViewArticle(@PathVariable String article) {
    final View view = viewRepository.findByArticle(article).orElse(viewMapper.emptyView(article));
    final ViewResponse viewResponse = viewMapper.mapToViewResponse(view);
    return HttpResponse.ok(viewResponse);
  }

  @Post
  @Transactional
  public HttpResponse<ViewResponse> postViewArticle(@PathVariable String article) {
    final View view = viewRepository.findByArticle(article).orElse(viewMapper.emptyView(article));
    viewRepository.save(viewMapper.incCountOfViews(view));
    final ViewResponse viewResponse = viewMapper.mapToViewResponse(view);
    broadcaster.broadcastAsync(new ArticleViewMessage(view.getArticle(), view.getCount()));
    return HttpResponse.ok(viewResponse);
  }

}
