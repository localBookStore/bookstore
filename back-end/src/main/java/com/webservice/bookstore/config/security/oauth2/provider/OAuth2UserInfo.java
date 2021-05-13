package com.webservice.bookstore.config.security.oauth2.provider;

public interface OAuth2UserInfo {

    String getProvider();
    String getEmail();
    String getName();
//    String getImageUrl();

}
