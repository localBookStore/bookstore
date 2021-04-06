package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.exception.UnauthorizedException;
import com.webservice.bookstore.service.BoardService;
import com.webservice.bookstore.service.ReplyService;
import com.webservice.bookstore.web.dto.ReplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")

public class ReplyController {

    @Autowired
    private final ReplyService replyService;

    @Autowired
    private final BoardService boardService;

    @PostMapping({"/board/reply/comment/","/board/reply/comment"})
    public ResponseEntity<String> replyRegister(@RequestBody ReplyDTO replyDTO){

        log.info("replyDto : 댓글 등록", replyDTO);
        replyService.registerReply(replyDTO);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }
    @PutMapping("/board/reply/modify")
    public ResponseEntity<String> replyModify(@RequestBody ReplyDTO replyDTO,
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            throw new UnauthorizedException("인증 오류가 발생했습니다.");
        } else if(!customUserDetails.isEnabled()) {
            throw new UnauthorizedException("계정이 잠겨있습니다. 관리자에게 문의해주시길 바랍니다.");
        }
        String email = customUserDetails.getMember().getEmail();
        if(!replyService.changeReply(replyDTO,email))
            return new ResponseEntity<>("접근할수 없는 방법입니다.",HttpStatus.FORBIDDEN);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }
    @DeleteMapping("/board/reply/delete")
    public ResponseEntity<String> replyDelete(@RequestBody ReplyDTO replyDTO,
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            throw new UnauthorizedException("인증 오류가 발생했습니다.");
        } else if(!customUserDetails.isEnabled()) {
            throw new UnauthorizedException("계정이 잠겨있습니다. 관리자에게 문의해주시길 바랍니다.");
        }

        String email = customUserDetails.getMember().getEmail();
        if(!replyService.deleteReply(replyDTO,email))
            return new ResponseEntity<>("접근할수 없는 방법입니다.",HttpStatus.FORBIDDEN);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }

}

