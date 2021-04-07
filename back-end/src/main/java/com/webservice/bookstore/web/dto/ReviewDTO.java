package com.webservice.bookstore.web.dto;

import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long id;

    private String content;

    private Integer score;

    private String memberNickName;

    private String memberEmail;

    private Long memberId;

    private Long itemId;

    private String itemName;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public static ReviewDTO entityToDTO(Review review) {

        return ReviewDTO.builder()
                .id(review.getId())
                .content(review.getContent())
                .score(review.getScore())
                .memberNickName(review.getMember().getNickName())
                .memberEmail(review.getMember().getEmail())
                .memberId(review.getMember().getId())
                .itemId(review.getItem().getId())
                .itemName(review.getItem().getName())
                .createdDate(review.getCreatedDate())
                .modifiedDate(review.getModifiedDate())
                .build();
    }

    public static Review toEntity(ReviewDTO dto) {

        return Review.builder().id(dto.getId())
                .content(dto.getContent())
                .score(dto.getScore())
                .member(Member.builder().id(dto.getMemberId()).build())
                .item(Item.builder().id(dto.getItemId()).build())
                .build();
    }

}
