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

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Coupon> coupons = new ArrayList<>();

//
//    private String certificated;

    public Member updateMemberInfo(String name, String imageUrl) {
        this.nickName = name;
        this.imageUrl = imageUrl;
        return this;
    }

    public void addCoupon(Coupon coupon) {
        this.coupons.add(coupon);
    }

    public void usedCoupon(Coupon coupon) {
        this.coupons.stream().forEach(savedCoupon -> {
            if(savedCoupon.getId() == coupon.getId()) {
                savedCoupon.isUsed(true);
                return;
            }
        });
    }

    public void validateCoupon(Coupon coupon) {
        this.coupons.stream().forEach(savedCoupon -> {
            if(savedCoupon.getId() == coupon.getId()) {
                if(savedCoupon.getIsUsed()) {
                    throw new DuplicateKeyException("사용한 쿠폰입니다.");
                }
            }
        });
    }



    public void updateRefreshToke(String refreshTokenValue) {
        this.refreshTokenValue= refreshTokenValue;
    }

}
