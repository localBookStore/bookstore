package com.webservice.bookstore.domain.entity.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ImageRepository extends JpaRepository<Image,Long> , QuerydslPredicateExecutor<Image> {
}
