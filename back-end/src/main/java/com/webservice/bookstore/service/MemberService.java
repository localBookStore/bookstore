package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.member.AuthProvider;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.exception.DuplicateUserException;
import com.webservice.bookstore.exception.SimpleFieldError;
import com.webservice.bookstore.util.RedisUtil;
import com.webservice.bookstore.web.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    @Transactional
    public void signup(EmailDto.SignUpRequest signUpRequest) {

        log.info("signup memberDto : " + signUpRequest);


        Member member = Member.builder()
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .nickName(signUpRequest.getNickName())
                .address(signUpRequest.getAddress())
                .phone(signUpRequest.getPhone())
                .provider(AuthProvider.DEFAULT)
                .enabled(Boolean.TRUE)
                .build();


        memberRepository.save(member);
    }

    @Transactional
    public void duplicatedEmail(String email) {
        if(this.memberRepository.existsByEmail(email)) {
            throw new DuplicateUserException("사용 중인 이메일 입니다.", new SimpleFieldError("email", "사용중인 이메일"));
        }
    }

    @Transactional
    public void withdraw(String email, String password) {
        Member member = this.memberRepository.findByEmail(email).get();
        String encodePassword = encoder.encode(password);
        if(password != null) {
            if(!member.getPassword().equals(encodePassword)) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        }
        redisUtil.deleteData(email);
        this.memberRepository.withdraw(email);
    }
}
