package com.webservice.bookstore.config.security.auth;

import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {

        Optional<Member> member = memberRepository.findByUserid(userid);

        if(member == null || member.isEmpty()) {
            throw new UsernameNotFoundException(userid);
        }
        Member savedMember = member.get();

        return new CustomUserDetails(savedMember);

    }
}
