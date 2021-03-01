package com.webservice.bookstore.iteminsert;

import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.json.JsonData;
import com.webservice.bookstore.domain.entity.item.ItemRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InsertItem {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void JsonInsert() throws Exception{
        JsonData data = new JsonData("c://input3.json");
        JSONArray jsonArray = data.getJsonArray();

        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = (JSONObject)jsonArray.get(i);
            Long qunatity = (Long)jsonObject.get("quantity");
            Long price = ((Long)jsonObject.get("price"));
            String author = (String)jsonObject.get("author");
            String isbn = (String)jsonObject.get("isbn");
            String name = (String)jsonObject.get("name");
            String publisher = (String)jsonObject.get("publisher");
            String description = (String)jsonObject.get("description");
            String imageUrl = (String)jsonObject.get("imageUrl");
            Long category = (Long)jsonObject.get("category");
            Category newCategory =Category.builder().id(category).build();
            Item item = Item.builder().name(name)
                    .price(price.intValue())
                    .publisher(publisher)
                    .author(author)
                    .isbn(isbn)
                    .description(description)
                    .quantity(qunatity.intValue())
                    .imageUrl(imageUrl)
                    .category(newCategory)
                    .build();
            itemRepository.save(item);

        }
    }
}
