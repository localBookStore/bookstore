package com.webservice.bookstore.domain.entity.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ItemQueryRespositoryTest {

    @Autowired
    ItemQueryRespository itemQueryRespository;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 책_검색_동적쿼리() throws Exception {
        //given
        String bookName = "JPA ORM";
        String author = "johangjin";

        Item item = Item.builder()
                .name(bookName)
                .author(author)
                .category(null)
                .price(10000)
                .description("책")
                .imageUrl(null)
                .publisher("hangjin")
                .isbn("123123")
                .quantity(3)
                .build();

        Item item2 = Item.builder()
                .name("")
                .author("")
                .category(null)
                .price(10000)
                .description("책")
                .imageUrl(null)
                .publisher("hangjin")
                .isbn("123123")
                .quantity(3)
                .build();

        itemRepository.saveAll(Arrays.asList(item, item2));

        //when
        PageRequest pageRequest = PageRequest.of(0,5);
        Page<Item> items =  this.itemQueryRespository.findDynamicBooks(new ItemSearch(bookName, author), pageRequest);
        List<Item> content = items.getContent();

        //then
        items.forEach(i -> System.out.println("item: "+ i));
        assertThat(items.getSize()).isEqualTo(5);
        assertThat(items.getTotalPages()).isEqualTo(1);
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getAuthor()).isEqualTo(author);
        assertThat(content.get(0).getName()).isEqualTo(bookName);
    }


}