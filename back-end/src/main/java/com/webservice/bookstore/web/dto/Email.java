package com.webservice.bookstore.web.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Email {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class SignUpRequest {

        @NotBlank(message = "이메일을 입력해주세요")
        @javax.validation.constraints.Email(message = "이메일 형식이 잘못되었습니다.")
        private String email;

        @Size(min = 1, max = 20, message = "닉네임을 입력해주세요.")
        private String nickName;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
        private String address;
        private String phone;
        private String certificated;
    }


    @Data
    public static class EmailCerticatedDto {
        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailCheckDto {

        @NotBlank(message = "이메일을 입력해주세요")
        @javax.validation.constraints.Email(message = "이메일 형식이 잘못되었습니다.")
        private String Email;
    }


    @Getter
    @Setter
    public static class CeriticateCode {

        private String certificated;
    }
}
