package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemQueryRespository;
import com.webservice.bookstore.domain.entity.item.ItemSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemQueryRespository itemQueryRespository;

    public Page<Item> searchBooks(ItemSearch itemSearch, Pageable pageable) {
        return itemQueryRespository.findDynamicBooks(itemSearch, pageable);
    }
}
