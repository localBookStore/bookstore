package com.webservice.bookstore.domain.entity.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 조회수_증가_테스트() throws Exception {
        //given
        Item book = Item.builder()
                .name("DATABASE BOOk")
                .author("아무개")
                .category(null)
                .imageUrl(null)
                .isbn("12344")
                .price(3)
                .quantity(3)
                .description("최고의 책")
                .publisher("한빛미디어")
                .build();
        Item savedItem = itemRepository.save(book);


        //when
        itemRepository.improveViewCount(savedItem.getId());
        Item item = itemRepository.findById(savedItem.getId()).get();


        //then
        assertThat(item.getViewCount()).isEqualTo(1);
    }


}