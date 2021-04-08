package com.webservice.bookstore.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.reply.ReviewReply;
import com.webservice.bookstore.domain.entity.review.Review;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewReplyDTO {

    private Long id;

    private String content;

    private Long reviewId;

    private String memberNickName;

    private String memberEmail;

    private Long memberId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedDate;

    public static ReviewReplyDTO entityToDTO(ReviewReply entity) {

        return ReviewReplyDTO.builder()
                .id(entity.getId())
                .reviewId(entity.getReview().getId())
                .content(entity.getContent())
                .memberNickName(entity.getMember().getNickName())
                .memberEmail(entity.getMember().getEmail())
                .createdDate(entity.getCreatedDate())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }
    public static ReviewReply toEntity(ReviewReplyDTO dto) {
        return ReviewReply.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .member(Member.builder().id(dto.getMemberId()).build())
                .review(Review.builder().id(dto.getReviewId()).build())
                .build();
    }
}
