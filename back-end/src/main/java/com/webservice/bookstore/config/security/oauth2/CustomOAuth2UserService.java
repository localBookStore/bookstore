package com.webservice.bookstore.config.security.oauth2;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.config.security.oauth2.provider.GoogleOAuth2UserInfo;
import com.webservice.bookstore.config.security.oauth2.provider.KakaoOAuth2UserInfo;
import com.webservice.bookstore.config.security.oauth2.provider.NaverOAuth2UserInfo;
import com.webservice.bookstore.config.security.oauth2.provider.OAuth2UserInfo;
import com.webservice.bookstore.domain.entity.member.AuthProvider;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.domain.entity.member.MemberRole;
import com.webservice.bookstore.exception.OAuth2AuthenticationProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("getClientRegistration : " + userRequest.getClientRegistration());
        log.info("getAccessToken : " + userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("oAuth2User : " + oAuth2User);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }

    }

    // 시용자 정보 추출
    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        String registrationId   // google, naver, kakao
                = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        log.info("Who is Provider? : " + registrationId);

        OAuth2UserInfo oAuth2UserInfo = null;
        if(registrationId.equals("GOOGLE")) {
            oAuth2UserInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
        } else if (registrationId.equals("NAVER")) {
            oAuth2UserInfo = new NaverOAuth2UserInfo(oAuth2User.getAttributes());
        } else if (registrationId.equals("KAKAO")) {
            oAuth2UserInfo = new KakaoOAuth2UserInfo(oAuth2User.getAttributes());
        } else {
            throw new OAuth2AuthenticationProcessingException(registrationId + "(으)로 로그인 할 수 없습니다.");
        }

        if(oAuth2UserInfo.getEmail() == null || oAuth2UserInfo.getEmail().isEmpty()) {
            throw new OAuth2AuthenticationProcessingException("지원하고 있는 " + registrationId + "에서 email을 찾을 수 없습니다.");
        }

        String userid = oAuth2UserInfo.getProvider() + oAuth2UserInfo.getProviderId();
        Optional<Member> optionalMember = memberRepository.findByUserid(userid);

        Member memberEntity;

        if(optionalMember.isPresent()) {

            memberEntity = optionalMember.get();

            if(!memberEntity.getProvider().equals(AuthProvider.valueOf(registrationId))) {
                throw new OAuth2AuthenticationProcessingException(memberEntity.getProvider() + "계정을 사용하기 위해서 로그인을 해야합니다.");
            }
            log.info("DB에 존재할 경우, 변경된 정보만 업데이트");
            log.info("ID 확인 : " + memberEntity.getUserid());
            // DB에 존재할 경우, 변경된 정보만 업데이트
            memberEntity.updateMemberInfo(oAuth2UserInfo.getName());

        } else {
            log.info("DB에 존재하지않으므로 바로 회원가입");
            // DB에 존재하지 않을 경우 강제 회원가입
            memberEntity = registerNewMember(userRequest, oAuth2UserInfo);
        }

        return new CustomUserDetails(memberEntity, oAuth2User.getAttributes());
    }

    private Member registerNewMember(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {

        Member newMember = Member.builder()
                                .name(oAuth2UserInfo.getName())
                                .userid(oAuth2UserInfo.getProvider() + oAuth2UserInfo.getProviderId())
                                .email(oAuth2UserInfo.getEmail())
                                .role(MemberRole.valueOf("USER"))
                                .provider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase()))
                                .providerId(oAuth2UserInfo.getProviderId())
                                .build();

        return memberRepository.save(newMember);
    }

}