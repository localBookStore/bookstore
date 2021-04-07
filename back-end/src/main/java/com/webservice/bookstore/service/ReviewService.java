package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.domain.entity.review.Review;
import com.webservice.bookstore.domain.entity.review.ReviewRepository;
import com.webservice.bookstore.web.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    public boolean registerReview(ReviewDTO dto){
        Optional<Member> op = memberRepository.findByEmail(dto.getMemberEmail());
        if(!op.isPresent())
            return false;
        Member member = op.get();
        dto.setMemberId(member.getId());
        Review review = ReviewDTO.toEntity(dto);
        reviewRepository.save(review);
        return true;
    }
}
