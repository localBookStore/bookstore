package com.webservice.bookstore.domain.entity.coupon;

import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.order.Orders;
import com.webservice.bookstore.domain.entity.orderItem.OrderItem;
import com.webservice.bookstore.exception.AfterDateException;
import com.webservice.bookstore.exception.ValidationException;
import com.webservice.bookstore.web.dto.CouponDto;
import lombok.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"member"})
@EqualsAndHashCode(of = "id")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int discountRate;
    private LocalDate endDate;

    @Builder.Default
    private Boolean isUsed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "coupon", fetch = FetchType.LAZY)
    private Orders order;

    public void setOrder(Orders order) {
        this.order = order;
    }

    public void isUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public void addMember(Member member) {
        this.member = member;
    }

    public void validateCoupon(List<FieldError> errors) {
        if(LocalDate.now().isAfter(this.getEndDate())) {
            throw new ValidationException("날짜가 지난 쿠폰입니다.", errors);
        } else if (this.isUsed) {
            throw new ValidationException("이미 사용한 쿠폰입니다.", errors);
        }
    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_item_id")
//    private OrderItem orderItem;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id")
//    private Category category;
}
