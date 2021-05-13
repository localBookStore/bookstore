package com.webservice.bookstore.iteminsert;

import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.category.CategoryRepository;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.json.JsonData;
import com.webservice.bookstore.domain.entity.item.ItemRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
public class InsertItem {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void insertCategories(){
        String categoryNames[]={"층류","철학","종교","사회과확","자연과학","기술과학","예술","언어","문학","역사"};

        for(Long i=new Long(0);i<categoryNames.length;i++){
            Category category = Category.builder().id(i).name(categoryNames[i.intValue()]).build();
            categoryRepository.save(category);
        }

    }
    @Test
    @Commit
    @Transactional
    void JsonInsert() throws Exception{
        String categoryNames[]={"층류","철학","종교","사회과확","자연과학","기술과학","예술","언어","문학","역사"};

        for(Long i=new Long(0);i<categoryNames.length;i++){
            Category category = Category.builder().id(i).name(categoryNames[i.intValue()]).build();
            categoryRepository.save(category);
        }
        JsonData data = new JsonData("/Users/johangjin/workspace/input.txt");
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
                    .publicationDate(randLocalDate())
                    .uploadImageName(isbn + ".jpg")
                    .build();
            itemRepository.save(item);

        }
    }

    @Test
    public void 랜덤_날짜() throws Exception {
        //given

        //when

        //then
        LocalDate localDate = randLocalDate();
        System.out.println(localDate);
    }


    private LocalDate randLocalDate() {
        long minDay = LocalDate.of(2011, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2021, 3, 1).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return randomDate;
    }
}
