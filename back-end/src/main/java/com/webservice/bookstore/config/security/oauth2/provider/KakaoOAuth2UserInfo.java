package com.webservice.bookstore.config.security.oauth2.provider;

import java.util.Map;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = (Map) attributes.get("kakao_account");
    }

    @Override
    public String getProvider() {
        return "KAKAO";
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

    @Override
    public String getName() {
        Map profile = (Map) attributes.get("profile");
        return String.valueOf(profile.get("nickname"));
    }

    @Override
    public String getImageUrl() {
        return String.valueOf(this.attributes.get("imageUrl"));
    }
}
