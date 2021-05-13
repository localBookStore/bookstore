package com.webservice.bookstore.domain.entity.member;

import com.webservice.bookstore.domain.entity.BaseTimeEntity;
import com.webservice.bookstore.domain.entity.coupon.Coupon;
import lombok.*;
import org.springframework.dao.DuplicateKeyException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "coupons")
@EqualsAndHashCode(of = "id")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickName;

    private String password;

    private String name;

    private String birth;

    private String imageUrl;

    private String address;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private Boolean enabled;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coupon> coupons = new ArrayList<>();

    public void addCoupon(Coupon coupon) {
        this.coupons.add(coupon);
        coupon.addMember(this);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void reMembership(String password, String nickName) {
        this.password = password;
        this.nickName = nickName;
        this.enabled  = true;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    public void changeImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
