package com.webservice.bookstore.web.dto;

import com.webservice.bookstore.domain.entity.member.AuthProvider;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRole;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MemberDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Default {
        private Long id;
        private String email;
        private String nickName;
        private String password;
        private String address;
        private String role;
        private String provider;
        private boolean enabled;

        // Entity -> DTO
        public static MemberDto.Default of(Member member) {
            return MemberDto.Default.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .nickName(member.getNickName())
                    .password(member.getPassword())
                    .address(member.getAddress())
                    .role(String.valueOf(member.getRole()))
                    .provider(String.valueOf(member.getProvider()))
                    .enabled(member.getEnabled())
                    .build();
        }

        // DTO -> Entity
        public Member toEntity() {
            return Member.builder()
                         .id(this.id)
                         .email(this.email)
                         .nickName(this.nickName)
                         .password(this.password)
                         .address(this.address)
                         .role(MemberRole.valueOf(this.role))
                         .provider(AuthProvider.valueOf(this.provider))
                         .enabled(this.enabled)
                         .build();
        }

    }

    @Getter @Setter
    @Builder
    public static class MyInfoRequest {
        private String email;
        private String nickName;
        private String address;
        private String provider;
        private String imageUrl;

        // Entity -> MyInfoRequest
        public static MyInfoRequest of(Member member) {
            return MyInfoRequest.builder()
                                .email(member.getEmail())
                                .nickName(member.getNickName())
                                .address(member.getAddress())
                                .provider(String.valueOf(member.getProvider()))
                                .imageUrl(member.getImageUrl())
                                .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Modify {
        private String email;

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(min = 2, max = 10, message = "최소 2자리, 최대 10자리 이상의 닉네임을 입력해주세요")
        private String nickName;

        @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}",
                 message = "숫자 + 특문 + 영문 8자 이상 조합으로 입력해주세요.")
        private String currentPassword;

        @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}",
                 message = "숫자 + 특문 + 영문 8자 이상 조합으로 입력해주세요.")
        private String newPassword;

        private String image;

    }

}
