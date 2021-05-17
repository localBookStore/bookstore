package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.member.AuthProvider;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.domain.entity.member.MemberRole;
import com.webservice.bookstore.exception.DuplicateUserException;
import com.webservice.bookstore.exception.MatchUserPasswordException;
import com.webservice.bookstore.exception.PreventRemembershipException;
import com.webservice.bookstore.exception.SimpleFieldError;
import com.webservice.bookstore.util.EmailUtil;
import com.webservice.bookstore.util.FileUtil;
import com.webservice.bookstore.util.RedisUtil;
import com.webservice.bookstore.web.dto.EmailDto;
import com.webservice.bookstore.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
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
    private final FileUtil<com.webservice.bookstore.web.dto.ItemDto.ItemAddDto, MemberDto.Modify> fileUtil;
    private final RedisUtil redisUtil;

    /*
    회원가입
    */
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
                                  .birth(signUpRequest.getBirth())
                                  .role(MemberRole.USER)
                                  .provider(AuthProvider.DEFAULT)
                                  .enabled(Boolean.TRUE)
                                  .build();
            memberRepository.save(member);
        }
    }

    /*
    이메일 계정 중복검사
    */
    public void duplicatedEmail(String email) {
        if(this.memberRepository.existsByEmail(email)) {
            throw new DuplicateUserException("사용 중인 이메일 입니다.", new SimpleFieldError("email", "사용중인 이메일"));
        }
    }

    /*
    회원 탈퇴
    */
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
    회원정보 수정
    */
    public MemberDto.MyInfoRequest modifyMyInfo(MemberDto.Modify memberDto) throws Exception  {

        Member member = this.memberRepository.findByEmail(memberDto.getEmail())
                                                .orElseThrow(() -> new EntityNotFoundException());

        if(String.valueOf(memberDto.getProvider()).equals("DEFAULT")) {
            if (!StringUtils.isBlank(memberDto.getCurrentPassword())) {

                if (!encoder.matches(memberDto.getCurrentPassword(), member.getPassword())) {
                    throw new MatchUserPasswordException("비밀번호가 일치하지 않습니다.",
                            new SimpleFieldError("password", "비밀번호 변경"));
                } else if (StringUtils.isNotBlank(memberDto.getNewPassword())) {
                    member.changePassword(encoder.encode(memberDto.getNewPassword()));
                }
            }
        }
        member.changeNickName(memberDto.getNickName());
        String imageUrl = memberDto.getImageUrl();
        if(!StringUtils.isBlank(imageUrl)) {
            String imageDataBytes = imageUrl.substring(imageUrl.indexOf(",") + 1);
            String contentType = imageUrl.substring(0, imageUrl.indexOf(";"));
            String extension = contentType.substring(contentType.indexOf("/") + 1);

            if(extension.equals("jpeg")) extension = "jpg";
            String newFileName = fileUtil.makeProfileName(member.getId());
            String path = fileUtil.checkStaticFilePath() + "profile/";
            fileUtil.deleteImageFile(member.getImageUrl(), path); // 기존 프로필 이미지 파일 삭제
            member.changeImage(newFileName + "." + extension);
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes)));
            fileUtil.checkImageType(newFileName, contentType, bufferedImage);
        }

        return MemberDto.MyInfoRequest.of(member);
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

    public String findId(String birth, String nickName) {
        Member member = this.memberRepository.findByBirthAndNickName(birth, nickName).orElseThrow(() -> new NullPointerException("조건에 부합하는 아이디는 존재하지 않습니다."));
        return member.getEmail();
    }

}
