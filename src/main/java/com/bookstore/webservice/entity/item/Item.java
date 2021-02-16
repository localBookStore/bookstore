package com.bookstore.webservice.entity.item;

import com.bookstore.webservice.entity.ItemCategory;
import com.bookstore.webservice.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;

    @OneToMany(mappedBy = "item")
    private List<ItemCategory> itemCategories;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer price;

    private Integer quantity;

}
