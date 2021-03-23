package com.creativeyann17.github.api.controllers;

import com.creativeyann17.github.api.domain.View;
import com.creativeyann17.github.api.models.ViewResponse;
import com.creativeyann17.github.api.repositories.ViewRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MicronautTest
public class ViewControllerTest {

  private static final String ARTICLE = "article";

  @Inject
  private EmbeddedServer server;

  @Inject
  @Client("/views")
  private HttpClient client;

  @Inject
  private ViewRepository viewRepository;

  @MockBean(ViewRepository.class)
  public ViewRepository viewRepository() {
    return Mockito.mock(ViewRepository.class);
  }

  @Test
  @DisplayName("GET - unknown article - 200 with 0 views")
  public void getViewArticle1() {
    final HttpResponse<ViewResponse> response = client.toBlocking().exchange(HttpRequest.GET(ARTICLE), ViewResponse.class);
    final ViewResponse viewResponse = response.getBody().get();
    assertEquals(HttpStatus.OK, response.getStatus());
    assertEquals(ARTICLE, viewResponse.getArticle());
    assertEquals(0, viewResponse.getCount());
  }

  @Test
  @DisplayName("GET - existing article - 200 with 1 views")
  public void getViewArticle2() {
    final View existingArticle = View.builder().article(ARTICLE).count(1).build();

    when(viewRepository.findByArticle(anyString())).thenReturn(Optional.of(existingArticle));

    final HttpResponse<ViewResponse> response = client.toBlocking().exchange(HttpRequest.GET(ARTICLE), ViewResponse.class);
    final ViewResponse viewResponse = response.getBody().get();

    verify(viewRepository).findByArticle(eq(ARTICLE));

    assertEquals(HttpStatus.OK, response.getStatus());
    assertEquals(ARTICLE, viewResponse.getArticle());
    assertEquals(1, viewResponse.getCount());
  }

  @Test
  @DisplayName("POST - unknown article - 200 created with 1 views")
  public void postViewArticle1() {

    when(viewRepository.findByArticle(anyString())).thenReturn(Optional.empty());

    final HttpResponse<ViewResponse> response = client.toBlocking().exchange(HttpRequest.POST(ARTICLE, ""), ViewResponse.class);
    final ViewResponse viewResponse = response.getBody().get();

    verify(viewRepository).findByArticle(eq(ARTICLE));

    assertEquals(HttpStatus.OK, response.getStatus());
    assertEquals(ARTICLE, viewResponse.getArticle());
    assertEquals(1, viewResponse.getCount());
  }

  @Test
  @DisplayName("POST - existing article - 200 incremented to 2 views")
  public void postViewArticle2() {
    final View existingArticle = View.builder().article(ARTICLE).count(1).build();

    when(viewRepository.findByArticle(anyString())).thenReturn(Optional.of(existingArticle));

    final HttpResponse<ViewResponse> response = client.toBlocking().exchange(HttpRequest.POST(ARTICLE, ""), ViewResponse.class);
    final ViewResponse viewResponse = response.getBody().get();

    verify(viewRepository).findByArticle(eq(ARTICLE));
    verify(viewRepository).save(eq((existingArticle)));

    assertEquals(HttpStatus.OK, response.getStatus());
    assertEquals(ARTICLE, viewResponse.getArticle());
    assertEquals(2, viewResponse.getCount());
  }

}