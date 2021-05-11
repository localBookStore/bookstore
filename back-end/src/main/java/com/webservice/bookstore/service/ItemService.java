package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.item.*;
import com.webservice.bookstore.web.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.core.ApplicationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemQueryRespository itemQueryRespository;
    private final ResourceLoader resourceLoader;
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
    public ItemDto.Default addItem(ItemDto.ItemAddDto itemDto) throws Exception {
        String imageUrl = itemDto.getImage();
        if(StringUtils.isNotEmpty(imageUrl)) {
            String imageDataBytes = imageUrl.substring(imageUrl.indexOf(",") + 1);
            String contentType = imageUrl.substring(0, imageUrl.indexOf(";"));
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes)));
            checkImageType(itemDto, contentType, bufferedImage);
        }

        Item item = itemDto.toEntity();
        Item savedItem = itemRepository.save(item);

        return ItemDto.Default.of(savedItem);
    }

    private void checkImageType(ItemDto.ItemAddDto itemDto, String contentType, BufferedImage bufferedImage) throws Exception {
        String path = System.getProperty("user.dir") + "/src/main/resources/static/" + itemDto.getIsbn();
        if (contentType.contains("image/jpeg")) {
            ImageIO.write(bufferedImage, "jpg", new File(path + ".jpg"));
        } else if (contentType.contains("image/png")) {
            ImageIO.write(bufferedImage, "png", new File(path + ".png"));
        } else if (contentType.contains("image/gif")) {
            ImageIO.write(bufferedImage, "gif", new File(path + ".gif"));
        }
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
