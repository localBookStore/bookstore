package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.member.AuthProvider;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.domain.entity.member.MemberRole;
import com.webservice.bookstore.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public MemberDto signup(MemberDto memberDto) {

        log.info("signup memberDto : " + memberDto);

        memberDto.setPassword(encoder.encode(memberDto.getPassword()));
        memberDto.setRole(String.valueOf(MemberRole.USER));
        memberDto.setProvider(String.valueOf(AuthProvider.DEFAULT));

        Member savedMember = memberRepository.save(memberDto.toEntity());
        memberDto = MemberDto.of(savedMember);

        return memberDto;
    }

}
