package com.webservice.bookstore.insertcategory;

import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InsertCategory {
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
}
