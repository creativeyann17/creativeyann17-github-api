package com.creativeyann17.github.api.graphql;

import com.creativeyann17.github.api.controllers.ViewController;
import com.creativeyann17.github.api.models.ViewResponse;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetViewDataFetcher implements DataFetcher<ViewResponse> {

  @Inject
  private ViewController viewController;

  @Override
  public ViewResponse get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {
    return viewController.getViewArticle((String) dataFetchingEnvironment.getArgument("article")).body();
  }
}
