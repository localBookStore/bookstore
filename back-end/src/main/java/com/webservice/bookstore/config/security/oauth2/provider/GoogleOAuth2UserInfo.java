package com.webservice.bookstore.config.security.oauth2.provider;

import java.util.Map;

public class GoogleOAuth2UserInfo implements OAuth2UserInfo {

    // PrincipalOauth2UserService 클래스에서 OAuth2User가 가지고 있는 attributes () :
    // 'oAuth2User.getAttributes()'
    private Map<String, Object> attributes;

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "GOOGLE";
    }

    @Override
    public String getEmail() {
        return this.attributes.get("email").toString();
    }

    @Override
    public String getName() {
        return this.attributes.get("name").toString();
    }

    @Override
    public String getImageUrl() {
        return this.attributes.get("picture").toString();
    }
}
