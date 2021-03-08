package com.webservice.bookstore.domain.entity.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemSearch {

    private String bookName;
    private String author;
}
