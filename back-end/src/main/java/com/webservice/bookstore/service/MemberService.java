package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto signup(MemberDto memberDto) {

        memberDto.setRole("ROLE_USER");
        Member member = memberDto.toEntity();

        Member savedMember = memberRepository.save(member);
        memberDto = MemberDto.of(savedMember);

        return memberDto;
    }
}
