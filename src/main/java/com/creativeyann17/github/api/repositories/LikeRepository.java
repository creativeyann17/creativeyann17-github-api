package com.creativeyann17.github.api.repositories;

import com.creativeyann17.github.api.domain.Like;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface LikeRepository extends CrudRepository<Like, Long> {
  Optional<Like> findByArticle(String article);
}
