package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.exception.ValidationException;
import com.webservice.bookstore.service.MemberService;
import com.webservice.bookstore.util.EmailUtil;
import com.webservice.bookstore.util.RedisUtil;
import com.webservice.bookstore.web.dto.SignUpRequest;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/signup")
@CrossOrigin(origins = {"http://localhost:3000/"})
public class MemberController {

    private final MemberService memberService;
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    @PostMapping
    public ResponseEntity signup(@RequestBody SignUpRequest signUpRequest, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new ValidationException("회원가입 유효성 실패", bindingResult.getFieldErrors());
        }

        memberService.signup(signUpRequest);

        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @PostMapping("/duplicated")
    public ResponseEntity duplicatedEmail(@RequestBody EmailCheckDto emailCheckDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            throw new ValidationException("이메일 형식이 맞지 않습니다.", bindingResult.getFieldErrors());
        }

        memberService.duplicatedEmail(emailCheckDto.getEmail());
        return ResponseEntity.ok("이메일 중복 체크 성공");

    }

    @PostMapping("/request-certificated")
    public ResponseEntity RequestCertificatedEmail(@RequestBody EmailCerticatedDto email) {

        log.info("email: " + email);
        String certificated = String.valueOf(EmailUtil.randomint());
        EmailUtil.sendEmail(javaMailSender, email.getEmail(), certificated);
        redisUtil.setData(certificated, certificated);

        return ResponseEntity.ok("이메일 인증 요청 메일을 보냈습니다.");
    }


    @PostMapping("/check-certificated")
    public ResponseEntity ResponseCertificatedEmail(@RequestBody CeriticateCode ceriticateCode) {

        String certificateCode = ceriticateCode.getCertificated();
        String savedCode = redisUtil.getData(certificateCode);
        if (!certificateCode.equals(savedCode)) {
            throw new IllegalArgumentException("인증코드가 맞지 않습니다.");
        }

        redisUtil.deleteRefreshToken(certificateCode);
        return ResponseEntity.ok("인증 성공하였습니디.");

    }

    @Data
    static class EmailCerticatedDto {
        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class EmailCheckDto {

        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "이메일 형식이 잘못되었습니다.")
        private String Email;
    }


    @Getter @Setter
    static class CeriticateCode {

        private String certificated;
    }


}
