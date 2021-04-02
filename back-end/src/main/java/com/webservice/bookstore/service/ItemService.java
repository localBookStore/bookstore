package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemQueryRespository;
import com.webservice.bookstore.domain.entity.item.ItemRepository;
import com.webservice.bookstore.domain.entity.item.ItemSearch;
import com.webservice.bookstore.web.dto.ItemAddDto;
import com.webservice.bookstore.web.dto.ItemDto;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemQueryRespository itemQueryRespository;

    private final ItemRepository itemRepository;

    public List<Item> searchBooks(ItemSearch itemSearch) {
        return itemQueryRespository.findDynamicBooks(itemSearch);
    }

    public Optional<Item> findById(Long id) {
        Optional<Item> item = this.itemRepository.findById(id);
        return item;
    }

    @Transactional
    public void improveViewCount(Long id) {
        this.itemRepository.improveViewCount(id);
    }

    public List<Item> bestItems() {
        return this.itemRepository.getBestItems();
    }

    public List<Item> getNewItems() {
        return this.itemRepository.getNewItems();
    }


    public List<ItemDto> getRandomList(int cnt) {
        List<Item> list=itemRepository.getThisMonthbooks(cnt);
        List<ItemDto> res = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            res.add(ItemDto.of(list.get(i)));
        }
        return res;
    }

    public List<ItemDto> getRandomListByGenre() {

        List<Item> itemList = itemRepository.getRandomListByGenre();

        List<ItemDto> itemDtoList = new ArrayList<>();
        for (Item item : itemList) {
            itemDtoList.add(ItemDto.of(item));
        }

        return itemDtoList;
    }

    public List<ItemDto> getListByGenre(Long category_id) {

        List<Item> itemList = itemRepository.findByCategoryId(category_id);

        List<ItemDto> itemDtoList = new ArrayList<>();
        for(Item item : itemList) {
            ItemDto dto = ItemDto.of(item);
            itemDtoList.add(dto);
        }

        return itemDtoList;
    }

    public List<ItemDto> findItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemDto> collect = items.stream().map(item -> ItemDto.of(item)).collect(Collectors.toList());
        return collect;
    }

    @Transactional
    public ItemDto addItem(ItemAddDto itemDto) {
        Item item = itemDto.toEntity();
        Item savedItem = itemRepository.save(item);
        return ItemDto.of(savedItem);
    }

    @Transactional
    public void modifyItem(ItemDto itemDto) {
        Item item = itemDto.toEntity();
        itemRepository.save(item);
    }

    @Transactional
    public ItemDto deleteItem(Item item) {
        itemRepository.delete(item);
        ItemDto itemDto = ItemDto.of(item);
        return itemDto;

    }
}
