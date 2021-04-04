package com.creativeyann17.github.api.graphql;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.io.ResourceResolver;

import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Factory
public class GraphQLFactory {

  @Bean
  @Singleton
  public GraphQL graphQL(ResourceResolver resourceResolver,
                         GetLikeDataFetcher getLikeDataFetcher, PostLikeDataFetcher postLikeDataFetcher,
                         GetViewDataFetcher getViewDataFetcher, PostViewDataFetcher postViewDataFetcher) {

    SchemaParser schemaParser = new SchemaParser();
    SchemaGenerator schemaGenerator = new SchemaGenerator();

    // Parse the schema.
    TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
    typeRegistry.merge(schemaParser.parse(new BufferedReader(new InputStreamReader(
        resourceResolver.getResourceAsStream("classpath:schema.graphqls").get()))));

    // Create the runtime wiring.
    RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
        .type("QueryRoot", typeWiring -> typeWiring
            .dataFetcher("getLikeArticle", getLikeDataFetcher)
            .dataFetcher("getViewArticle", getViewDataFetcher))
        .type("MutationRoot", typeWiring -> typeWiring
            .dataFetcher("postLikeArticle", postLikeDataFetcher)
            .dataFetcher("postViewArticle", postViewDataFetcher))
        .build();

    // Create the executable schema.
    GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);

    // Return the GraphQL bean.
    return GraphQL.newGraphQL(graphQLSchema).build();
  }
}