package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.domain.entity.reply.Reply;
import com.webservice.bookstore.domain.entity.reply.ReviewReply;
import com.webservice.bookstore.domain.entity.reply.ReviewReplyRepository;
import com.webservice.bookstore.domain.entity.review.Review;
import com.webservice.bookstore.domain.entity.review.ReviewRepository;
import com.webservice.bookstore.web.dto.ReviewReplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewReplyService {

    private final ReviewReplyRepository reviewReplyRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public boolean registerReply(ReviewReplyDTO dto,String loginEmail){
        Optional<Member> op = memberRepository.findByEmail(loginEmail);
        if(!op.isPresent())
            return false;
        Member member = op.get();
        Review review = reviewRepository.getOne(dto.getReviewId());
        if(review.getMember().getRole().getRoleName().equals("ROLE_USER")){//사용자가 유저라면
            if(!review.getMember().equals(loginEmail))//리뷰 작성자와 다르면
                return false;
        }
        dto.setMemberId(member.getId());//admin인경우도 있으니 바꿔줘야함
        ReviewReply reviewReply = ReviewReplyDTO.toEntity(dto);
        reviewReplyRepository.save(reviewReply);
        return true;
    }

    @Transactional
    public boolean modifyReply(ReviewReplyDTO dto,String loginEmail){
        Optional<Member> op = memberRepository.findByEmail(loginEmail);
        if(!op.isPresent())
            return false;
        Member member = op.get();
        Review review = reviewRepository.getOne(dto.getReviewId());
        if(!review.getMember().equals(loginEmail))//리뷰 작성자와 다르면
            return false;
        ReviewReply reviewReply = ReviewReplyDTO.toEntity(dto);
        return true;
    }

    @Transactional
    public boolean removeReply(ReviewReplyDTO dto,String loginEmail){
        return true;
    }


}
