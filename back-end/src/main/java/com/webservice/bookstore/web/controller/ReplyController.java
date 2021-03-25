package com.webservice.bookstore.web.controller;

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

    @PostMapping("/board/reply/comment/")
    public ResponseEntity<String> replyRegister(@RequestBody ReplyDTO replyDTO){

        log.info("replyDto : {}", replyDTO);
        replyService.registerReply(replyDTO);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }
}

