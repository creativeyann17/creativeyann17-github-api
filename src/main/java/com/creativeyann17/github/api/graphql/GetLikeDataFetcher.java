package com.creativeyann17.github.api.graphql;

import com.creativeyann17.github.api.controllers.LikeController;
import com.creativeyann17.github.api.models.LikeResponse;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetLikeDataFetcher implements DataFetcher<LikeResponse> {

  @Inject
  private LikeController likeController;

  @Override
  public LikeResponse get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {
    return likeController.getLikeArticle((String) dataFetchingEnvironment.getArgument("article")).body();
  }
}
