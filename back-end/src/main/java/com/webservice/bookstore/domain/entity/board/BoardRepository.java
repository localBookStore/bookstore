package com.webservice.bookstore.domain.entity.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long>, QuerydslPredicateExecutor<Board> {

    @Query("select b,i from Board b " +
            "inner join Image i on i.board = b "+
            "where b.id = :id")
    List<Object[]> getBoardwithImage(@Param("id") Long id);


}
