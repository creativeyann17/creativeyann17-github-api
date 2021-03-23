package com.creativeyann17.github.api.mappers;

import com.creativeyann17.github.api.domain.View;
import com.creativeyann17.github.api.models.ViewResponse;

import javax.inject.Singleton;

@Singleton
public class ViewMapper {

  public View emptyView(final String article) {
    return View.builder().article(article).count(0).build();
  }

  public ViewResponse mapToViewResponse(final View view) {
    return new ViewResponse(view.getArticle(), view.getCount());
  }

  public View incCountOfViews(final View view) {
    view.setCount(view.getCount() + 1);
    return view;
  }
}
