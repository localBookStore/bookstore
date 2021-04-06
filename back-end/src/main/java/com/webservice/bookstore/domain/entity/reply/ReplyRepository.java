package com.webservice.bookstore.domain.entity.reply;

import com.webservice.bookstore.domain.entity.board.Board;
import com.webservice.bookstore.web.dto.ReplyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long>, QuerydslPredicateExecutor<Reply> {

    //group순서중 가장 큰수
    @Query(value = "select case when max(group_order) is null then 0 else max(group_order) end as 'group_order' " +
            "from reply " +
            "where board_id = :boardId",nativeQuery = true)
    int getReplyOrder(@Param("boardId") long boardId);

    @Query(value = "select * from reply where board_id = :boardId " +
            "order by group_order asc ,depth asc ,created_date asc",nativeQuery = true)
    List<Reply> getBoardReplyList(@Param("boardId") long boardId);

    @Modifying
    @Transactional
    @Query(value = "delete from Reply where board_id = :board_id",nativeQuery = true)
    void deleteReplyByBoard(@Param("board_id") Long boardId);

}
