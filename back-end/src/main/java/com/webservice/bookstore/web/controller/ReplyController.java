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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")

public class ReplyController {

    @Autowired
    private final ReplyService replyService;

    @Autowired
    private final BoardService boardService;

    @PostMapping("/board/reply/comment/")
    public ResponseEntity<String> replyRegister(ReplyDTO replyDTO){

        log.info("replyDyo : {}", replyDTO);
        //replyService.registerReply(replyDTO);
        System.out.println("요청왓다-------------------------------------");
        System.out.println(replyDTO);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }
}

