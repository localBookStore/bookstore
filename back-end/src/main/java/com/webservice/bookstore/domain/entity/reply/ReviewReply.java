package com.webservice.bookstore.domain.entity.reply;

import com.webservice.bookstore.domain.entity.BaseTimeEntity;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.review.Review;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "review")
public class ReviewReply extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

}
