package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemQueryRespository;
import com.webservice.bookstore.domain.entity.item.ItemRepository;
import com.webservice.bookstore.domain.entity.item.ItemSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemQueryRespository itemQueryRespository;

    private final ItemRepository itemRepository;

    public Page<Item> searchBooks(ItemSearch itemSearch, Pageable pageable) {
        return itemQueryRespository.findDynamicBooks(itemSearch, pageable);
    }

    public Item findById(Long id) {
        Optional<Item> item = this.itemRepository.findById(id);
        Item savedItem = item.orElseGet(null);
        return savedItem;
    }

    @Transactional
    public void improveViewCount(Long id) {
        this.itemRepository.improveViewCount(id);
    }
}
