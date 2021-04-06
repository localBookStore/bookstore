package com.webservice.bookstore.config.security.oauth2;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.config.security.jwt.JwtUtil;
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
import org.apache.commons.lang3.StringUtils;
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


    /*
        OAuth2 공급자로부터 액세스 토큰을 얻은 후에 호출됩니다. Oauth2 공급자로부터 사용자의 세부정보를 가져옴.
        동일한 이메일을 사용하는 사용자가 이미 데이터베이스에 있으면 세부 정보를 업데이트하고 그렇지 않으면 새 사용자를 등록합니다.
     */
    @Transactional
    @Override
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
            // 존재하는 아이디가 아닐 때 발생하는 예외
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }

    }

    // 시용자 정보 추출
    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        String provider   // google, naver, kakao
                = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        log.info("Who is Provider? : " + provider);

        OAuth2UserInfo oAuth2UserInfo = null;
        if(provider.equals("GOOGLE")) {
            oAuth2UserInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
        } else if (provider.equals("NAVER")) {
            oAuth2UserInfo = new NaverOAuth2UserInfo(oAuth2User.getAttributes());
        } else if (provider.equals("KAKAO")) {
            oAuth2UserInfo = new KakaoOAuth2UserInfo(oAuth2User.getAttributes());
        } else {
            throw new OAuth2AuthenticationProcessingException(provider + "(으)로 로그인 할 수 없습니다.");
        }

        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException(provider + " 인증 서버에서 email을 찾을 수 없습니다.");
        }

        String email = oAuth2UserInfo.getEmail();
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        Member memberEntity = null;

        if(optionalMember.isEmpty()) {
            log.info("DB에 존재하지 않으므로 바로 회원가입");
            memberEntity = registerNewMember(oAuth2UserInfo);
        } else {
            memberEntity = optionalMember.get();

            // DB에서 조회한 계정이 일반 계정인지 판단
            if(!memberEntity.getProvider().equals(AuthProvider.valueOf(provider))) {
                throw new OAuth2AuthenticationProcessingException("\'"+ email +"\'계정은 이미 사용하려면 일반 로그인으로 수행해야합니다.");
            }
            log.info("DB에 존재할 경우, 변경된 정보만 업데이트");
            log.info("ID 확인 : " + memberEntity.getEmail());
            memberEntity.updateMemberInfo(oAuth2UserInfo.getName(), oAuth2UserInfo.getImageUrl());
        }

        return new CustomUserDetails(memberEntity, oAuth2User.getAttributes());
    }

    private Member registerNewMember(OAuth2UserInfo oAuth2UserInfo) {

        Member newMember = Member.builder()
                                .email(oAuth2UserInfo.getEmail())
                                .nickName(oAuth2UserInfo.getName())
                                .role(MemberRole.USER)
                                .provider(AuthProvider.valueOf(oAuth2UserInfo.getProvider()))
                                .enabled(true)  // OAuth 계정은 굳이 이메일 인증 절차가 필요없다고 판단
                                .build();

        return memberRepository.save(newMember);
    }

}