package com.webservice.bookstore.domain.entity.item;

import com.webservice.bookstore.domain.entity.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"member",})
@EqualsAndHashCode(of = "id")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;

    //private List<ItemCategory> itemCategories;

    private String description;

    private Integer price;

    private Integer quantity;

}