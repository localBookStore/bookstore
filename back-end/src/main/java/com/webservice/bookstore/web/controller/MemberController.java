package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.config.security.auth.CustomUserDetailsService;
import com.webservice.bookstore.service.MemberService;
import com.webservice.bookstore.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"http://localhost:3000/"})
public class MemberController {

    private final MemberService memberService;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping(value = "/signup/")
    public ResponseEntity signup(@RequestBody MemberDto dto) {

        log.info("Request signup " + dto);

        MemberDto resultDto = memberService.signup(dto);

        return new ResponseEntity(resultDto, HttpStatus.OK);
    }

//    @PostMapping(value = "/signin/", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<?> signin(@RequestBody MemberDto memberDto) {
////    public ResponseEntity<?> signInSubmit(@RequestBody Request request) {
//
////        log.info("[Request] user-sign-in [userId=" + request.getPayload().get("userId").asText() + ",deviceToken=" + request.getPayload().get("deviceToken").asText() + "]");
////        request.validateHeader("SignInRequest");
////        request.validatePayload();
////
////        String signInResult = userService.signIn(request.getPayload());
////        return new ResponseEntity<>(signInResult, HttpStatus.OK);
//    }

}
