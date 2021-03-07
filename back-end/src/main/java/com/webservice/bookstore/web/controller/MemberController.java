package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.service.MemberService;
import com.webservice.bookstore.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"http://localhost:3000/"})
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/signup/")
    public ResponseEntity signup(@RequestBody MemberDto dto) {

        MemberDto resultDto = memberService.signup(dto);

        return new ResponseEntity(resultDto, HttpStatus.OK);
    }
}
