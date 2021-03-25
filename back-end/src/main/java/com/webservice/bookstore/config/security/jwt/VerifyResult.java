package com.webservice.bookstore.config.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyResult {

    private String email;
    private String nickName;
    private boolean result;

}
