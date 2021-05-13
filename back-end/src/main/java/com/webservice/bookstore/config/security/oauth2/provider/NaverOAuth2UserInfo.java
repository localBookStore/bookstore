package com.webservice.bookstore.config.security.oauth2.provider;

import java.util.Map;

public class NaverOAuth2UserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = (Map) attributes.get("response");
    }

    @Override
    public String getProvider() {
        return "NAVER";
    }

    @Override
    public String getEmail() {
        return String.valueOf(this.attributes.get("email"));
    }

    @Override
    public String getName() {
        return String.valueOf(this.attributes.get("name"));
    }

//    @Override
//    public String getImageUrl() {
//        return String.valueOf(this.attributes.get("profile_image"));
//    }
}
