package com.webservice.bookstore.config.security.auth;

import com.webservice.bookstore.config.security.jwt.JwtUtil;
import com.webservice.bookstore.domain.entity.member.AuthProvider;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("CustomUserDetailsService.loadUserByUsername input parameter : " + email);

        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if(!optionalMember.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        Member member = optionalMember.get();
        if(!AuthProvider.DEFAULT.equals(member.getProvider())) {
            String msg = "해당 e-mail은 " + member.getProvider().toString().toLowerCase() + " 소셜 계정입니다.";
            throw new AuthenticationException(msg) {};
        }

        CustomUserDetails customUserDetails = CustomUserDetails.builder().member(optionalMember.get()).build();

        return customUserDetails;

    }
}
