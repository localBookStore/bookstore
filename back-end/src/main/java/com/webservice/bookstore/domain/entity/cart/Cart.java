package com.webservice.bookstore.domain.entity.cart;

import com.webservice.bookstore.domain.entity.BaseTimeEntity;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.member.Member;
import lombok.*;

import javax.persistence.*;

@NamedEntityGraph(
        name = "Cart.item",
        attributeNodes = @NamedAttributeNode("item")
)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"member","item"})
@EqualsAndHashCode(of = "id")
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer price;

    private Integer orderCount;

    // 장바구니 아이템 수량 업데이트 메소드 호출
    public void updateQuantity(int orderCount) {
        this.orderCount = orderCount;
    }

}
