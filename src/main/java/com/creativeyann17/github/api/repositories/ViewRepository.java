package com.creativeyann17.github.api.repositories;

import com.creativeyann17.github.api.domain.View;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface ViewRepository extends CrudRepository<View, Long> {
  Optional<View> findByArticle(String article);
}
