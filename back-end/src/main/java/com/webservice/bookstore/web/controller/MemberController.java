package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.exception.ValidationException;
import com.webservice.bookstore.service.MemberService;
import com.webservice.bookstore.util.EmailUtil;
import com.webservice.bookstore.util.RedisUtil;
import com.webservice.bookstore.web.dto.EmailDto;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class MemberController {

    private final MemberService memberService;
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    @PostMapping
    public ResponseEntity signup(@RequestBody @Valid EmailDto.SignUpRequest signUpRequest, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new ValidationException("회원가입 유효성 실패", bindingResult.getFieldErrors());
        }

        memberService.signup(signUpRequest);

        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @PostMapping("/signup/duplicated")
    public ResponseEntity duplicatedEmail(@RequestBody @Valid EmailDto.EmailCheckDto emailCheckDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            throw new ValidationException("이메일 형식이 맞지 않습니다.", bindingResult.getFieldErrors());
        }

        memberService.duplicatedEmail(emailCheckDto.getEmail());
        return ResponseEntity.ok("이메일 중복 체크 성공");

    }

    @PostMapping("/signup/request-certificated")
    public ResponseEntity RequestCertificatedEmail(@RequestBody EmailDto.EmailCerticatedDto email) {

        log.info("email: " + email);
        String certificated = String.valueOf(EmailUtil.randomint());
        EmailUtil.sendEmail(javaMailSender, email.getEmail(), certificated);
        redisUtil.setData(certificated, certificated, 80L);

        return ResponseEntity.ok("이메일 인증 요청 메일을 보냈습니다.");
    }


    @PostMapping("/signup/check-certificated")
    public ResponseEntity ResponseCertificatedEmail(@RequestBody EmailDto.CeriticateCode ceriticateCode) {

        String certificateCode = ceriticateCode.getCertificated();
        String savedCode = redisUtil.getData(certificateCode);
        if (!certificateCode.equals(savedCode)) {
            throw new IllegalArgumentException("인증코드가 맞지 않습니다.");
        }

        redisUtil.deleteData(certificateCode);
        return ResponseEntity.ok("인증 성공하였습니디.");

    }

    @PostMapping("/withdrawal")
    public ResponseEntity withDrawal(@RequestBody(required = false) WithdrawalRequest withdrawalRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();
        String password = customUserDetails.getPassword();
        this.memberService.withdraw(email, password);
        return ResponseEntity.ok("정상적으로 회원탈퇴하였습니다.");
    }


    @Data
    static class WithdrawalRequest {
        private String password;
    }
}
