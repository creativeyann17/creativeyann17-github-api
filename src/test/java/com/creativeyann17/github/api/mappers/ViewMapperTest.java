package com.creativeyann17.github.api.mappers;

import com.creativeyann17.github.api.domain.View;
import com.creativeyann17.github.api.models.ViewResponse;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class ViewMapperTest {

  @Inject
  private ViewMapper mapper;

  @Test
  public void emptyView() {
    final View mapped = mapper.emptyView("foo");
    assertEquals("foo", mapped.getArticle());
    assertEquals(0, mapped.getCount());
  }

  @Test
  public void mapToViewResponse() {
    final View view = View.builder().article("foo").count(1).build();
    final ViewResponse mapped = mapper.mapToViewResponse(view);
    assertEquals("foo", mapped.getArticle());
    assertEquals(1, mapped.getCount());
  }

  @Test
  public void incCountOfViews() {
    final View before = View.builder().article("foo").count(1).build();
    final View after = mapper.incCountOfViews(before);
    assertEquals("foo", after.getArticle());
    assertEquals(2, after.getCount());
  }

}