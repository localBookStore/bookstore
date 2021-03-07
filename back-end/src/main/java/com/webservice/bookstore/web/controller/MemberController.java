package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class MemberController {

    private final MemberService memberService;

}
