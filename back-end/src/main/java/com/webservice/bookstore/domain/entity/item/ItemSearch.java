package com.webservice.bookstore.domain.entity.item;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemSearch {

    private String name;
    private String author;
}
