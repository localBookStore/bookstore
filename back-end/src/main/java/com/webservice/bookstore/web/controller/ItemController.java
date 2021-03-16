package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.domain.entity.item.*;
import com.webservice.bookstore.service.ItemService;
import com.webservice.bookstore.web.dto.GetItemDto;
import com.webservice.bookstore.web.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Log4j2
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping(value = "/api/items/", produces = MediaTypes.HAL_JSON_VALUE+";charset=utf-8")
public class ItemController {

    private final ItemService itemService;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getSearchItems(@RequestParam(value = "tag") String tag, @RequestParam(value = "input") String input) {
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


    @GetMapping("{id}")
    public ResponseEntity getItem(@PathVariable Long id) {
        this.itemService.improveViewCount(id);
        Optional<Item> savedItem = itemService.findById(id);
        if(savedItem == null || savedItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Item newItem = savedItem.get();
        GetItemDto itemDto = GetItemDto.toDto(newItem);
        ItemResource itemResource = new ItemResource(itemDto);
//        itemResource.add(linkTo(ItemController.class).slash(savedItem.getId()).withRel("purchase-item"));
        return ResponseEntity.ok(itemResource);
    }



}
