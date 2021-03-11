package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemRepository;
import com.webservice.bookstore.web.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemServices{
    private final ItemRepository itemRepository;

    @Override
    public List<ItemDto> getRandomList(int cnt) {
        List<Item> list=itemRepository.getThisMonthbooks(cnt);
        List<ItemDto> res = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            res.add(entityToDto(list.get(i)));
        }
        return res;
    }

    @Override
    public List<ItemDto> getRandomListByGenre(ItemDto itemDto) {

        List<Item> itemListByGenre = itemRepository.getRandomListByGenre(itemDto.getCategory_id());

        List<ItemDto> res = new ArrayList<>();
        for (Item item : itemListByGenre) {
            res.add(entityToDto(item));
        }

        return res;
    }

    @Override
    public List<ItemDto> getListByGenre(Long category_id) {

        List<Item> itemList = itemRepository.findAllByCategoryId(category_id);

        List<ItemDto> itemDtoList = new ArrayList<>();
        for(Item item : itemList) {
            ItemDto dto = entityToDto(item);
            itemDtoList.add(dto);
        }

        return itemDtoList;
    }
}
