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

    public void getItemSearch(String tag, String input) {
        if(tag.equals("name")) {
            this.setName(input);
        } else if(tag.equals("author")){
            this.setAuthor(input);
        }
//        return this;
    }
}
