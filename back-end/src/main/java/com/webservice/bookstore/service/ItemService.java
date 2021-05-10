package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.FileHandler;
import com.webservice.bookstore.domain.entity.item.*;
import com.webservice.bookstore.web.dto.ItemDto;
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

    private final ItemPictureRepository itemPictureRepository;

    private FileHandler fileHandler;

    public List<Item> searchBooks(ItemSearch itemSearch) {
        return itemQueryRespository.findDynamicBooks(itemSearch);
    }

    public Optional<Item> findById(Long id) {
        Optional<Item> item = this.itemRepository.findById(id);
        return item;
    }

    @Transactional
    public void improveViewCount(Long id) {
        Item item = this.itemRepository.findById(id).orElseThrow(NullPointerException::new);
        item.addViewCount(item.getViewCount());

//        this.itemRepository.improveViewCount(id);
    }

    public List<Item> bestItems() {
        return this.itemRepository.getBestItems();
    }

    public List<Item> getNewItems() {
        return this.itemRepository.getNewItems();
    }


    public List<ItemDto.Default> getRandomList(int cnt) {
        List<Item> list=itemRepository.getThisMonthbooks(cnt);
        List<ItemDto.Default> res = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            res.add(ItemDto.Default.of(list.get(i)));
        }
        return res;
    }

    public List<ItemDto.Default> getRandomListByGenre() {

        List<Item> itemList = itemRepository.getRandomListByGenre();

        List<ItemDto.Default> itemDtoList = new ArrayList<>();
        for (Item item : itemList) {
            itemDtoList.add(ItemDto.Default.of(item));
        }

        return itemDtoList;
    }

    public List<ItemDto.Default> getListByGenre(Long category_id) {

        List<Item> itemList = itemRepository.findByCategoryId(category_id);

        List<ItemDto.Default> itemDtoList = new ArrayList<>();
        for(Item item : itemList) {
            ItemDto.Default dto = ItemDto.Default.of(item);
            itemDtoList.add(dto);
        }

        return itemDtoList;
    }

    public List<ItemDto.Default> findItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemDto.Default> collect = items.stream().map(ItemDto.Default::of).collect(Collectors.toList());
        return collect;
    }

    @Transactional
    public ItemDto.Default addItem(ItemDto.ItemAddDto itemDto) throws Exception{
        Item item = itemDto.toEntity();
        Item savedItem = itemRepository.save(item);
        List<ItemPicture> list = fileHandler.parseFile(savedItem.getId(), itemDto.getImages());
        if(list.isEmpty()) {

        } else {
            List<ItemPicture> pictures = new ArrayList<>();
            for(ItemPicture itemPicture : list) {
                pictures.add(itemPictureRepository.save(itemPicture));
            }
            item.setItemPicture(pictures);
        }

        return ItemDto.Default.of(savedItem);
    }

    @Transactional
    public void modifyItem(ItemDto.Default itemDto) {
        Item item = itemDto.toEntity();
        itemRepository.save(item);
    }

    @Transactional
    public List<ItemDto.Default> deleteItem(List<Long> ids) {
        itemRepository.deleteIn(ids);
        List<Item> remainItems = itemRepository.findAll();
        List<ItemDto.Default> reaminItemDtos = remainItems.stream().map(ItemDto.Default::of).collect(Collectors.toList());
        return reaminItemDtos;
    }
}
