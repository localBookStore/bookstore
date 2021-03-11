package com.webservice.bookstore.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemRepository;
import com.webservice.bookstore.domain.entity.item.ItemSearch;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.itemRepository.deleteAll();
    }

    @Test
    @DisplayName("책 검색 테스트")
    public void searchBooks() throws Exception {
        //given
        String bookName = "JPA ORM";
        String author = "johangjin";
        ItemSearch itemSearch = new ItemSearch(bookName, author);
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

        //then
        this.mockMvc
                .perform(get("/api/items/")
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemSearch))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.itemList[0].name").exists())
                .andExpect(jsonPath("_embedded.itemList[0].id").value(1))
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_links.self").exists())
        ;
    }
    
    /*
       책 검색 조회
    *  Not found Error 테스트 
    */
    @Test
    @DisplayName("검색바에서 조회시 없을때 404 응답 받기")
    public void searchBooks_404() throws Exception {
        //given
        ItemSearch itemSearch = ItemSearch.builder()
                .name("최고의 디비책")
                .build();

        //when

        //then
        this.mockMvc.perform(get("/api/items/")
                .param("page","0")
                .param("size","10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemSearch))
        )
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("기존의 책 하나 조회하기")
    public void getItem() throws Exception {
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


        //when
        this.itemRepository.save(book);

        //then
        this.mockMvc.perform(get("/api/items/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("DATABASE BOOk"))
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("author").value("아무개"))
                .andExpect(jsonPath("_links.purchase-item").exists())
        ;

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

        //when
        Item savedItem = this.itemRepository.save(book);

        //then
        this.mockMvc.perform(get("/api/items/{id}",savedItem.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("viewCount").value(1));
    }


    @Test
    @DisplayName("best 카테고리 정상적인 조회")
    public void bestItems() throws Exception {
        //given
        IntStream.rangeClosed(1,10).forEach(this::saveItem);

        //when
        List<Item> items = this.itemRepository.bestItems();

        //then
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


//    private void generateItem(Long i) {
//        Item item = Item.builder()
//                .name("JPA BOOK " + i)
//                .description("책")
//                .quantity(3)
//                .price(3)
//                .isbn(null)
//                .imageUrl(null)
//                .category(null)
//                .author("아무개")
//                .build();
//        this.itemRepository.save(item);
//    }


}