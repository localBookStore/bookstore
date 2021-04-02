package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.coupon.Coupon;
import com.webservice.bookstore.domain.entity.member.AuthProvider;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.domain.entity.member.MemberRole;
import com.webservice.bookstore.exception.DuplicateUserException;
import com.webservice.bookstore.exception.PreventRemembershipException;
import com.webservice.bookstore.exception.SimpleFieldError;
import com.webservice.bookstore.util.EmailUtil;
import com.webservice.bookstore.util.RedisUtil;
import com.webservice.bookstore.web.dto.EmailDto;
import com.webservice.bookstore.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    public void signup(EmailDto.SignUpRequest signUpRequest) {

        log.info("signup memberDto : " + signUpRequest);

        Optional<Member> optionalMember = this.memberRepository.findByEmail(signUpRequest.getEmail());
        if(optionalMember.isPresent()) {
            Member member = optionalMember.get();
            int elapsedWithdrawalTime = LocalDateTime.now().compareTo(member.getModifiedDate());
            log.info("현재 날짜 : " + LocalDateTime.now());
            log.info("회원탈퇴 날짜 : " + member.getModifiedDate());  // 회원탈퇴 시 수정 날짜 업데이트가 안됨...
            log.info("회원탈퇴 경과시간 : " + elapsedWithdrawalTime);
            if(elapsedWithdrawalTime < 31) {
                throw new PreventRemembershipException("해당 계정은 " + (31 - elapsedWithdrawalTime) + "일 후에 재등록이 가능합니다",
                                                        new SimpleFieldError("email", "회원가입 재등록 불가"));
            } else {
                member.reMembership(encoder.encode(signUpRequest.getPassword()),
                                    signUpRequest.getNickName());
            }
        } else {
            Member member = Member.builder()
                                  .email(signUpRequest.getEmail())
                                  .password(encoder.encode(signUpRequest.getPassword()))
                                  .nickName(signUpRequest.getNickName())
                                  .role(MemberRole.USER)
                                  .provider(AuthProvider.DEFAULT)
                                  .enabled(Boolean.TRUE)
                                  .build();
            memberRepository.save(member);
        }
    }

    public void duplicatedEmail(String email) {
        if(this.memberRepository.existsByEmail(email)) {
            throw new DuplicateUserException("사용 중인 이메일 입니다.", new SimpleFieldError("email", "사용중인 이메일"));
        }
    }

    public void withdraw(String email, String password) {
        Member member = this.memberRepository.findByEmail(email).get();
        if(password != null) {
            if(!encoder.matches(password, member.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        }
        redisUtil.deleteData(email);
        this.memberRepository.withdraw(email);
    }

    /*
    비밀번호 찾기
    */
    public String searchPassword(EmailDto.findPwdRequest findPwdRequest) {

        log.info("find pwd memberDto : " + findPwdRequest);

        Member member = this.memberRepository.findByEmailAndNickName(findPwdRequest.getEmail(),
                                                                     findPwdRequest.getNickName())
                                             .orElseThrow(() -> new EntityNotFoundException());

        String tempPassword = EmailUtil.randomString();
        member.changePassword(encoder.encode(tempPassword));

        return tempPassword;

    }

    public List<MemberDto.Default> findAllMembers() {
        List<Member> memberEntityList = memberRepository.findAllByRoleNot(MemberRole.ADMIN);
        List<MemberDto.Default> memberDtoList = new ArrayList<>();
        memberEntityList.stream().forEach(member -> memberDtoList.add(MemberDto.Default.of(member)));

        return memberDtoList;
    }
}
