package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.member.AuthProvider;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.util.EmailUtil;
import com.webservice.bookstore.web.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;

    @Transactional
    public void signup(SignUpRequest signUpRequest) {

        log.info("signup memberDto : " + signUpRequest);

        String certificated = String.valueOf(EmailUtil.randomint());

        Member member = Member.builder()
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .nickName(signUpRequest.getNickName())
                .address(signUpRequest.getAddress())
                .phone(signUpRequest.getPhone())
                .provider(AuthProvider.DEFAULT)
                .enabled(Boolean.FALSE)
                .certificated(certificated)
                .build();

        EmailUtil.sendEmail(javaMailSender, member.getEmail(), certificated);

        memberRepository.save(member);
    }

}
