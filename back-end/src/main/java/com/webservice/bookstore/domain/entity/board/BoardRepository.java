package com.webservice.bookstore.domain.entity.board;

import com.webservice.bookstore.web.dto.BoardDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long>, QuerydslPredicateExecutor<Board> {

    @Query("select b,i from Board b " +
            "inner join Image i on i.board = b "+
            "where b.id = :id")
    List<Object[]> getBoardwithImage(@Param("id") Long id);


    @Transactional
    @Modifying
    @Query("update Board set content =:content, " +
            "category=:category, title=:title,modifiedDate = now() where id=:id")
    void modifyBoard(@Param("content") String content,
                     @Param("category") String category,
                     @Param("title") String title,
                     @Param("id") Long id);
    @Modifying
    @Transactional
    @Query("delete from Board where id=:id")
    void deleteBoard(@Param("id") Long id);

    @Query(value = "select b,m from Board b join Member m on b.member.id=m.id where m.id=:id")
    List<Object[]> getMemberBoardList(@Param("id")Long memberId);


}
