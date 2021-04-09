package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemRepository;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.domain.entity.reply.Reply;
import com.webservice.bookstore.domain.entity.review.Review;
import com.webservice.bookstore.domain.entity.review.ReviewRepository;
import com.webservice.bookstore.web.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    //-1잘못된접근 0,주문한적 없음, 1 성공
    public int registerReview(ReviewDTO dto) {

        //memberEmail , content , score ,itemid 가필요
        Optional<Member> op = memberRepository.findByEmail(dto.getMemberEmail());
        Optional<Item> op2 = itemRepository.findById(dto.getItemId());
        if (!op.isPresent()||!op2.isPresent())
            return -1;
        Member member = op.get();
        Item item = op2.get();
        Long numhas = reviewRepository.hasOrder(member.getId(), dto.getItemId());
        if (numhas == 0 || numhas == null)//주문한적없음
            return 0;
        dto.setMemberId(member.getId());
        dto.setMemberEmail(member.getEmail());
        dto.setMemberNickName(member.getNickName());
        dto.setItemName(item.getName());
        Review review = ReviewDTO.toEntity(dto);
        reviewRepository.save(review);
        return 1;
    }

    //로그인 이메일,reviewid와 score, content만 필요
    @Transactional
    public boolean modifyReview(ReviewDTO dto, String loginEmail) {
        if (loginEmail == null || loginEmail.length() == 0)
            return false;
        Review review = reviewRepository.getOne(dto.getId());
        if (!review.getMember().getEmail().equals(loginEmail))
            return false;
        review.setScore(dto.getScore());
        review.setContent(dto.getContent());
        reviewRepository.save(review);
        return true;
    }

    //리뷰아이디와 로그인이메일이면충분
    @Transactional
    public boolean deleteReview(ReviewDTO dto, String loginEmail) {
        if (loginEmail == null || loginEmail.length() == 0)
            return false;
        Review review = reviewRepository.getOne(dto.getId());
        if (!review.getMember().getEmail().equals(loginEmail))
            return false;
        reviewRepository.deleteById(dto.getId());
        return true;
    }

    @Transactional
    public List<ReviewDTO> getItemReviewList(Long itemId){
        List<Review> reviews = reviewRepository.getItemReviewList(itemId);
        List<ReviewDTO> dtoList = reviews.stream().map(entity-> ReviewDTO.entityToDTO(entity)).collect(Collectors.toList());
        return dtoList;
    }
}
