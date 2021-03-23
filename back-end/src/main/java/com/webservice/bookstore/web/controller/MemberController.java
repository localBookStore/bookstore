package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.config.security.jwt.JwtTokenProvider;
import com.webservice.bookstore.service.MemberService;
import com.webservice.bookstore.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"http://localhost:3000/"})
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/signup/")
    public ResponseEntity signup(@RequestBody MemberDto dto) {

        log.info("Request signup " + dto);

        MemberDto resultDto = memberService.signup(dto);

        return new ResponseEntity(resultDto, HttpStatus.OK);
    }

}
