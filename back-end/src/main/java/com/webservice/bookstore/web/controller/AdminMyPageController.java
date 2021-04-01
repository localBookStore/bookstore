package com.webservice.bookstore.web.controller;


import com.sun.mail.iap.Response;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemLinkResource;
import com.webservice.bookstore.domain.entity.item.ItemSearch;
import com.webservice.bookstore.service.ItemService;
import com.webservice.bookstore.web.dto.ItemAddDto;
import com.webservice.bookstore.web.dto.ItemDto;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/api/admin", produces = MediaTypes.HAL_JSON_VALUE+";charset=utf-8")
public class AdminMyPageController {

    private final ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity getAdminItems() {
        List<ItemDto> itemDtos = this.itemService.findItems();
        List<ItemLinkResource> itemLinkResources = itemDtos.stream().map(itemDto -> new ItemLinkResource(itemDto, linkTo(ItemController.class).slash(itemDto.getId()).withSelfRel())).collect(Collectors.toList());
        CollectionModel<ItemLinkResource> collectionModel = CollectionModel.of(itemLinkResources);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/items/search")
    public ResponseEntity getAdminSearchItems(@RequestParam(value = "tag") String tag, @RequestParam(value = "input") String input) {
        ItemSearch itemSearch = ItemSearch.builder()
                .build();
        if(tag.equals("name")) {
            itemSearch.setName(input);
        } else if(tag.equals("author")){
            itemSearch.setAuthor(input);
        }

        List<Item> items = this.itemService.searchBooks(itemSearch);
        if(items == null || items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ItemDto> collect = items.stream().map(item -> ItemDto.of(item)).collect(Collectors.toList());
        List<ItemLinkResource> itemLinkResources = collect.stream().map(itemDto -> new ItemLinkResource(itemDto, linkTo(ItemController.class).slash(itemDto.getId()).withSelfRel())).collect(Collectors.toList());
        CollectionModel<ItemLinkResource> result = CollectionModel.of(itemLinkResources);
        return ResponseEntity.ok(result);
    }


    @PostMapping(value = "/additem")
    public ResponseEntity addAdminItem(@RequestBody ItemAddDto itemDto) {
        this.itemService.addItem(itemDto);
        return ResponseEntity.ok("상품이 등록되었습니다.");
    }


    @PutMapping("/items")
    public ResponseEntity modifyItem(@RequestBody ItemDto itemDto) {
        itemService.modifyItem(itemDto);
        return ResponseEntity.ok("상품이 수정되었습니다.");

    }


    @DeleteMapping("/items/{id}")
    public ResponseEntity deleteItem(@PathVariable Long id) {
        Item item = itemService.findById(id).orElseThrow(() -> new NullPointerException("해당 상품은 존재하지 않습니다."));
        ItemDto itemDto = itemService.deleteItem(item);
        return ResponseEntity.ok(itemDto);

    }




}
