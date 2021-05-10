package com.webservice.bookstore.domain.entity.item;

import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.exception.NotEnoughStockException;
import com.webservice.bookstore.exception.SimpleFieldError;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemPicture> itemPicture = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder.Default
    private int viewCount = 0;

    private LocalDate publicationDate;

    public void setItemPicture(List<ItemPicture> itemPicture) {
        this.itemPicture = itemPicture;
        for (ItemPicture picture : itemPicture) {
            picture.setItem(this);
        }
    }

    /* (주문 생성 시) 재고량(stock) 감소 */
    public void removeStockQuantity(int quantity) {
        this.quantity -= quantity;
        if(this.quantity < 0) {
            throw new NotEnoughStockException("We don't have enough stock.",
                                                new SimpleFieldError("quantity", "재고량 부족"));
        }
    }

    /*
    (주문 취소 시) 재고량(stock) 증가
    */
    public void addStockQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void addViewCount(int viewCount) {
        this.viewCount  += 1;
    }

}
