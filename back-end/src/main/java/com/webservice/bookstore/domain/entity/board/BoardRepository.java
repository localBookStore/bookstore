package com.webservice.bookstore.domain.entity.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardRepository extends JpaRepository<Board,Long>, QuerydslPredicateExecutor<Board> {

}
