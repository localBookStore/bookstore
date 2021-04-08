package com.webservice.bookstore.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webservice.bookstore.domain.entity.board.Board;
import com.webservice.bookstore.domain.entity.reply.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long id;

    private String content; //댓글 내용

    private Long memberId; //작성자 키값

    private String memberEmail; //작성자 아이디

    private int depth; //깊이

    private int groupOrder; //댓글그룹 순서

    private long boardId;

    private long parent; //부모
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedDate;

    public static Reply toEntity(ReplyDTO replyDTO, Board board){
        return Reply.builder()
                .content(replyDTO.getContent())
                .depth(replyDTO.getDepth())
                .groupOrder(replyDTO.getGroupOrder())
                .memberEmail(replyDTO.getMemberEmail())
                .memberId(replyDTO.getMemberId())
                .board(board)
                .parent(replyDTO.getParent())
                .build();
    }
    public static ReplyDTO entityToDTO(Reply reply){
        return ReplyDTO.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .depth(reply.getDepth())
                .groupOrder(reply.getGroupOrder())
                .memberEmail(reply.getMemberEmail())
                .memberId(reply.getMemberId())
                .boardId(reply.getBoard().getId())
                .parent(reply.getParent())
                .createdDate(reply.getCreatedDate())
                .modifiedDate(reply.getModifiedDate())
                .build();
    }
}
