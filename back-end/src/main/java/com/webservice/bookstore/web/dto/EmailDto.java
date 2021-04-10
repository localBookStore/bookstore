package com.webservice.bookstore.web.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EmailDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpRequest {

        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "이메일 형식이 잘못되었습니다.")
        private String email;

        @Size(min = 1, max = 20, message = "닉네임을 입력해주세요.")
        private String nickName;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}",
        message = "숫자 + 특문 + 영문 8자 이상입니다")
        private String password;

        private String address;
        private String phone;
        @Pattern(regexp = "[0-9]{6}")
        private String birth;
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

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findPwdRequest {

        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "이메일 형식이 잘못되었습니다.")
        private String email;

        @Size(min = 1, max = 20, message = "닉네임을 입력해주세요.")
        private String nickName;
    }

}
