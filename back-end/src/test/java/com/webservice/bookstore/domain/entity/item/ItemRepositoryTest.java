package com.webservice.bookstore.domain.entity.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        this.itemRepository.deleteAll();
    }

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

    @Test
    @DisplayName("베스트 카테고리 아이템들 리스트 조회")
    public void bestItems() throws Exception {
        //given
        IntStream.rangeClosed(1,10).forEach(this::saveItem);

        //when
        List<Item> items = this.itemRepository.bestItems();

        //then
        assertThat(items.get(0).getViewCount()).isEqualTo(10);
        assertThat(items.get(9).getViewCount()).isEqualTo(1);
    }

    private void saveItem(int i) {
        Item item = Item.builder()
                .name("DATABASE BOOk")
                .author("아무개")
                .category(null)
                .imageUrl(null)
                .isbn("12344")
                .price(3)
                .viewCount(i)
                .quantity(3)
                .description("최고의 책")
                .publisher("한빛미디어")
                .build();
        this.itemRepository.save(item);
    }

}