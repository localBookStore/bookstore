package com.webservice.bookstore.iteminsert;

import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.json.JsonData;
import com.webservice.bookstore.repository.ItemRepository;
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
        JsonData data = new JsonData("c://input2.json");
        JSONArray jsonArray = data.getJsonArray();

        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = (JSONObject)jsonArray.get(i);
            Long qunatity = (Long)jsonObject.get("quantity");
            Integer price = Integer.parseInt((String)jsonObject.get("price"));
            String author = (String)jsonObject.get("author");
            String isbn = (String)jsonObject.get("isbn");
            String name = (String)jsonObject.get("name");
            String publisher = (String)jsonObject.get("publisher");
            String description = (String)jsonObject.get("description");
            String category = (String)jsonObject.get("category");

            Item item = Item.builder().name(name)
                    .price(price)
                    .publisher(publisher)
                    .author(author)
                    .isbn(isbn)
                    .description(description)
                    .quantity(qunatity.intValue())
                    .category(null)
                    .build();
            itemRepository.save(item);

        }
    }
}
