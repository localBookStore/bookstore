package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.category.Category;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.web.dto.ItemDto;

import java.util.List;

public interface ItemServices {

    List<ItemDto> getRandomList(int cnt);

    List<ItemDto> getRandomListByGenre();

    List<ItemDto> getListByGenre(Long category_id);
}