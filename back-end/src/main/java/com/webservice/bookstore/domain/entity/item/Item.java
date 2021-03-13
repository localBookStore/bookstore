package com.webservice.bookstore.domain.entity.item;

import com.webservice.bookstore.domain.entity.category.Category;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NamedEntityGraph(name = "Item.category",
        attributeNodes = @NamedAttributeNode("category"))
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"category"})
@EqualsAndHashCode(of = "id")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer price;

    private Integer quantity;

    private String isbn;

    private String publisher;

    private String author;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder.Default
    private int viewCount = 0;

    private LocalDate publicationDate;

    /* (주문 생성 시) 재고량(stock) 감소 */
    public void removeStockQuantity(int quantity) {
        this.quantity -= quantity;
        if(this.quantity < 0) {
            throw new NotEnoughStockException("We don't have enough stock.");   // 추후 수정할 예정
        }
    }

}
