package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.config.security.jwt.JwtUtil;
import com.webservice.bookstore.exception.ValidationException;
import com.webservice.bookstore.service.MemberService;
import com.webservice.bookstore.web.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
//@CrossOrigin(origins = {"http://localhost:3000"})
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @PostMapping(value = "/signup/")
    public ResponseEntity signup(@RequestBody SignUpRequest signUpRequest, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new ValidationException("회원가입 유효성 실패", bindingResult.getFieldErrors());
        }

        memberService.signup(signUpRequest);

        return new ResponseEntity("Success", HttpStatus.OK);
    }

}
