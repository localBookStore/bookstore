package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemResource;
import com.webservice.bookstore.domain.entity.item.ItemSearch;
import com.webservice.bookstore.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Log4j2
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping(value = "/api/items/", produces = MediaTypes.HAL_JSON_VALUE+";charset=utf-8")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity getSearchItems(@RequestBody ItemSearch itemSearch, Pageable pageable, PagedResourcesAssembler<Item> assembler) {
        Page<Item> items = this.itemService.searchBooks(itemSearch, pageable);
        if(items == null || items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        PagedModel<ItemResource> itemResources = assembler.toModel(items, item -> new ItemResource(item));
        return ResponseEntity.ok(itemResources);
    }

    @GetMapping("{id}")
    public ResponseEntity getItem(@PathVariable Long id) {
        this.itemService.improveViewCount(id);
        Item savedItem = itemService.findById(id);
        if(savedItem == null) {
            return ResponseEntity.notFound().build();
        }
        ItemResource itemResource = new ItemResource(savedItem);
        itemResource.add(linkTo(ItemController.class).slash(savedItem.getId()).withRel("purchase-item"));
        return ResponseEntity.ok(itemResource);
    }

    @GetMapping("/bestitems/")
    public ResponseEntity bestItems() {
        List<Item> items = this.itemService.bestItems();
        List<ItemResource> itemResources = items.stream().map(item -> new ItemResource(item)).collect(Collectors.toList());
        CollectionModel<ItemResource> collectionModel = CollectionModel.of(itemResources);
        return ResponseEntity.ok(collectionModel);
    }

}
