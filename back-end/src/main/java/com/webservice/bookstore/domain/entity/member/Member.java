package com.webservice.bookstore.domain.entity.member;

import com.webservice.bookstore.domain.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickName;

    private String password;

    private String imageUrl;

    private String address;

    private String phone;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @Lob
    @Column(length = 512)
    private String refreshTokenValue;

    private Boolean enabled;
//
//    private String certificated;

    public Member updateMemberInfo(String name, String imageUrl) {
        this.nickName = name;
        this.imageUrl = imageUrl;
        return this;
    }

    public void reMembership(String password, String nickName) {
        this.password = password;
        this.nickName = nickName;
        this.enabled  = true;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
