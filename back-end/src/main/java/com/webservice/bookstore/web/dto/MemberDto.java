package com.webservice.bookstore.web.dto;

import com.webservice.bookstore.domain.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MemberDto {

    private Long id;
    private String name;
    private String userid;
    private String password;
    private String email;
    private String address;
    private String phone;
    private String role;

    // Entity -> DTO
    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .userid(member.getUserid())
                .password(member.getPassword())
                .email(member.getEmail())
                .address(member.getAddress())
                .phone(member.getPhone())
                .role(member.getRole())
                .build();
    }

    // DTO -> Entity
    public Member toEntity() {
        return Member.builder()
                .id(this.id)
                .name(this.name)
                .userid(this.userid)
                .password(this.password)
                .email(this.email)
                .address(this.address)
                .phone(this.phone)
                .role(this.role)
                .build();
    }
}
