package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.exception.UnauthorizedException;
import com.webservice.bookstore.exception.ValidationException;
import com.webservice.bookstore.service.MemberService;
import com.webservice.bookstore.util.EmailUtil;
import com.webservice.bookstore.util.RedisUtil;
import com.webservice.bookstore.web.dto.EmailDto;
import com.webservice.bookstore.web.dto.MemberDto;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class MemberController {

    private final MemberService memberService;
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    @PostMapping("/signup")
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
        redisUtil.setData(certificated, certificated, 60L*5);

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
        this.memberService.withdraw(email, withdrawalRequest.getPassword());
        return ResponseEntity.ok("정상적으로 회원탈퇴하였습니다.");
    }


    @Data
    static class WithdrawalRequest {
        private String password;
    }

    // 이메일 찾기 -> 어떤 기준으로 이메일을 찾을지 정해야함. 예를 들어 이름 + nickname + 생일 등

    // 비밀번호 찾기
    @PostMapping("/searchpwd")
    public ResponseEntity searchpwd(@RequestBody @Valid EmailDto.findPwdRequest findPwdRequest,
                                    BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new ValidationException("비밀번호 찾기 요청 실패", bindingResult.getFieldErrors());
        }

        try {
            String tempPassword = this.memberService.searchPassword(findPwdRequest);
            EmailUtil.sendEmail(javaMailSender, findPwdRequest.getEmail(), tempPassword);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity("계정이 존재하지 않습니다", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity("임시 비밀번호 이메일 전송 완료", HttpStatus.OK);
    }

    @GetMapping("/mypage")
    public ResponseEntity searchMyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        verifyAuthentication(customUserDetails);

        Member member = customUserDetails.getMember();
        MemberDto.MyInfoRequest myInfoRequest = MemberDto.MyInfoRequest.builder()
                                                         .email(member.getEmail())
                                                         .nickName(member.getNickName())
                                                         .address(member.getAddress())
                                                         .provider(String.valueOf(member.getProvider()))
                                                         .build();

        return new ResponseEntity(myInfoRequest, HttpStatus.OK);
    }

    @GetMapping("/admin/members")
    public ResponseEntity searchMembers(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        verifyAuthentication(customUserDetails);

        List<MemberDto.Default> memberDtoList = memberService.findAllMembers();

        return new ResponseEntity(memberDtoList, HttpStatus.OK);
    }

    private void verifyAuthentication(CustomUserDetails customUserDetails) {
        if(customUserDetails == null || customUserDetails.equals("")) {
            throw new UnauthorizedException("인증 오류가 발생했습니다.");
        }
    }

}
