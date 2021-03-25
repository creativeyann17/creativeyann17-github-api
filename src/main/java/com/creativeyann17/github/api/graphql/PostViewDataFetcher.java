package com.creativeyann17.github.api.graphql;

import com.creativeyann17.github.api.controllers.ViewController;
import com.creativeyann17.github.api.models.ViewResponse;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PostViewDataFetcher implements DataFetcher<ViewResponse> {

  @Inject
  private ViewController viewController;

  @Override
  public ViewResponse get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {
    return viewController.postViewArticle((String) dataFetchingEnvironment.getArgument("article")).body();
  }
}
