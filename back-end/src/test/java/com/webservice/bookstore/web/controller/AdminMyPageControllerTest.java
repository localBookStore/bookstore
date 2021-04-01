package com.webservice.bookstore.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.category.CategoryRepository;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemRepository;
import com.webservice.bookstore.web.dto.ItemDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
class AdminMyPageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    public void 상품_검색_테스트() throws Exception {
        //given
        Category category = Category.builder()
                .id(10L)
                .name("총류")
                .build();
        categoryRepository.save(category);

        Category category2 = Category.builder()
                .id(2L)
                .name("역")
                .build();
        categoryRepository.save(category2);

        IntStream.rangeClosed(1,3).forEach(i -> {
            Item item = Item.builder()
                    .name("상품 " + i)
                    .category(category)
                    .quantity(5)
                    .description("하하")
                    .build();
            itemRepository.save(item);
        });
        IntStream.rangeClosed(4,8).forEach(i -> {
            Item item = Item.builder()
                    .name("상품 " + i)
                    .category(category2)
                    .quantity(5)
                    .description("하하")
                    .build();
            itemRepository.save(item);
        });

        //when

        //then
        this.mockMvc.perform(get("/api/admin/items"))
                .andDo(print())
                .andExpect(status().isOk())
                ;
    }


    @DisplayName("상품 등록")
    @Test
    public void test1() throws Exception {
        //given
        Category category = Category.builder()
                .id(10L)
                .name("총류")
                .build();
        categoryRepository.save(category);

        ItemDto item = ItemDto.builder()
                .name("좋은 책")
                .quantity(3)
                .category_id(category.getId())
                .description("하하하하")
                .build();

        //when

        //then
        mockMvc.perform(post("/api/admin/additem")
                        .content(objectMapper.writeValueAsString(item))
                        .contentType(MediaTypes.HAL_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(status().isCreated())
        ;
    }


    @DisplayName("상품 수정하기")
    @Test
    void modifyItem() throws Exception {

        Category category = Category.builder()
                .id(10L)
                .name("총류")
                .build();
        categoryRepository.save(category);

        Item item = Item.builder()
                .name("좋은 책")
                .quantity(3)
                .category(category)
                .description("하하하하")
                .build();
        itemRepository.save(item);

        ItemDto itemDto = ItemDto.builder()
                .id(item.getId())
                .name("굿꿋")
                .category_id(category.getId())
                .quantity(3)
                .description("나이스 아주 좋아")
                .build();


        mockMvc.perform(put("/api/admin/items")
                .content(objectMapper.writeValueAsString(itemDto))
                .contentType(MediaTypes.HAL_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk())
                ;

        System.out.println("==========================================");

        Item savedItem = this.itemRepository.findById(item.getId()).get();
        assertThat(savedItem.getName()).isEqualTo("굿꿋");
        System.out.println(savedItem);

    }

    @DisplayName("관리자가 상품 삭제하기")
    @Test
    public void test2() throws Exception {
        //given

        Category category = Category.builder()
                .id(10L)
                .name("총류")
                .build();
        categoryRepository.save(category);

        Item item = Item.builder()
                .name("좋은 책")
                .quantity(3)
                .category(category)
                .description("하하하하")
                .build();
        itemRepository.save(item);


        //when

        //then
        this.mockMvc.perform(delete("/api/admin/items/{id}", item.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
        ;

        /*
        * 삭제되었는지 확인하는 코드.
        *
        */
        assertThrows(NullPointerException.class, () -> {
            itemRepository.findById(item.getId()).orElseThrow(()->new NullPointerException("없습니다."));
        });
    }


}