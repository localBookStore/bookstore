package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.domain.entity.item.*;
import com.webservice.bookstore.service.ItemService;
import com.webservice.bookstore.web.dto.ItemDto;
import com.webservice.bookstore.web.resource.DefaultItemResource;
import com.webservice.bookstore.web.resource.GetItemResource;
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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/items/", produces = MediaTypes.HAL_JSON_VALUE+";charset=utf-8")
public class ItemController {

    private final ItemService itemService;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getSearchItems(@RequestParam(value = "tag") String tag, @RequestParam(value = "input") String input) {
        ItemSearch itemSearch = ItemSearch.builder().build();
        itemSearch.getItemSearch(tag, input);

        List<Item> items = this.itemService.searchBooks(itemSearch);
        if(items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ItemDto.Default> collect = items.stream().map(ItemDto.Default::of).collect(Collectors.toList());
        List<DefaultItemResource> defaultItemResources = collect.stream().map(itemDto -> new DefaultItemResource(itemDto, linkTo(ItemController.class).slash(itemDto.getId()).withSelfRel())).collect(Collectors.toList());
        CollectionModel<DefaultItemResource> result = CollectionModel.of(defaultItemResources);
        return ResponseEntity.ok(result);
    }




    @GetMapping("{id}")
    public ResponseEntity getItem(@PathVariable Long id) {
        this.itemService.improveViewCount(id);
        Optional<Item> savedItem = itemService.findById(id);
        if(savedItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Item newItem = savedItem.get();
        ItemDto.GetItemDto itemDto = ItemDto.GetItemDto.of(newItem);
        GetItemResource getItemResource = new GetItemResource(itemDto);
        getItemResource.add(linkTo(methodOn(OrdersController.class).createOrder(null,null)).withRel("purchase-item"));
        return ResponseEntity.ok(getItemResource);
    }
}
