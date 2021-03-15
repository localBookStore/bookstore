package com.webservice.bookstore.web.dto;


import com.webservice.bookstore.domain.entity.board.Board;
import com.webservice.bookstore.domain.entity.cart.Cart;
import com.webservice.bookstore.domain.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long id;

    private String title;

    private String content;

    private String memberUserid;//작성자 아이디

    private String replyCount; //게시글수

    private String category;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Long memberId;

    public static Board toEntity(BoardDTO boardDTO,Member member){
        return Board.builder().id(boardDTO.getId())
                .category(boardDTO.getCategory())
                .content(boardDTO.getContent())
                .title(boardDTO.getTitle())
                .member(member)
                .build();
    }
    public static BoardDTO entityToDTO(Board board){
        return BoardDTO.builder()
                .id(board.getId())
                .category(board.getCategory())
                .content(board.getContent())
                .title(board.getTitle())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .build();
    }
}
