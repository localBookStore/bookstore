package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.exception.UnauthorizedException;
import com.webservice.bookstore.service.ReviewService;
import com.webservice.bookstore.web.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @ExceptionHandler(Exception.class)
    @PostMapping("/items/register/review")
    public ResponseEntity<List<ReviewDTO>> reviewRegister(@RequestBody ReviewDTO dto){
        int num = reviewService.registerReview(dto);
        if(num < 0)
            throw new IllegalStateException("잘못된 요청");
        if(num ==0)
            throw new IllegalStateException("주문한 내역이없습니다.");
        return new ResponseEntity<>(reviewService.getItemReviewList(dto.getItemId()),HttpStatus.OK);
    }
    @ExceptionHandler(Exception.class)
    @PutMapping("/items/modify/review")
    public ResponseEntity<List<ReviewDTO>> modifyRegister(@RequestBody ReviewDTO dto,
                                                          @AuthenticationPrincipal CustomUserDetails customUserDetails){
        if(customUserDetails == null) {
            throw new UnauthorizedException("인증 오류가 발생했습니다.");
        } else if(!customUserDetails.isEnabled()) {
            throw new UnauthorizedException("계정이 잠겨있습니다. 관리자에게 문의해주시길 바랍니다.");
        }
        String email = customUserDetails.getMember().getEmail();
        if(!reviewService.modifyReview(dto,email))
            throw new UnauthorizedException("접근할수없습니다.");
        return new ResponseEntity<>(reviewService.getItemReviewList(dto.getItemId()),HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    @DeleteMapping("/items/delete/review")
    public ResponseEntity<List<ReviewDTO>> deleteRegister(@RequestBody ReviewDTO dto,
                                                          @AuthenticationPrincipal CustomUserDetails customUserDetails){
        if(customUserDetails == null) {
            throw new UnauthorizedException("인증 오류가 발생했습니다.");
        } else if(!customUserDetails.isEnabled()) {
            throw new UnauthorizedException("계정이 잠겨있습니다. 관리자에게 문의해주시길 바랍니다.");
        }
        String email = customUserDetails.getMember().getEmail();
        if(!reviewService.deleteReview(dto,email))
            throw new UnauthorizedException("접근할수없습니다.");
        return new ResponseEntity<>(reviewService.getItemReviewList(dto.getItemId()),HttpStatus.OK);
    }

    @GetMapping("/items/reviews/{itemId}")
    public ResponseEntity<List<ReviewDTO>> getItemReviews(@PathVariable("itemId")Long itemId){
        return new ResponseEntity<>(reviewService.getItemReviewList(itemId),HttpStatus.OK);
    }
    
    
}
