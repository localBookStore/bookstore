package com.webservice.bookstore.web.controller;


import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.web.resource.DefaultItemResource;
import com.webservice.bookstore.domain.entity.item.ItemSearch;
import com.webservice.bookstore.service.ItemService;
import com.webservice.bookstore.web.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/api/admin", produces = MediaTypes.HAL_JSON_VALUE+";charset=utf-8")
public class AdminMyPageController {

    private final ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity getAdminItems() {
        List<ItemDto.Default> itemDtos = this.itemService.findItems();
        List<DefaultItemResource> defaultItemResources = itemDtos.stream().map(itemDto -> new DefaultItemResource(itemDto, linkTo(ItemController.class).slash(itemDto.getId()).withSelfRel())).collect(Collectors.toList());
        return ResponseEntity.ok(defaultItemResources);
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
        List<ItemDto.Default> collect = items.stream().map(item -> ItemDto.Default.of(item)).collect(Collectors.toList());
        List<DefaultItemResource> defaultItemResources = collect.stream().map(itemDto -> new DefaultItemResource(itemDto, linkTo(ItemController.class).slash(itemDto.getId()).withSelfRel())).collect(Collectors.toList());
        return ResponseEntity.ok(defaultItemResources);
    }


    @PostMapping(value = "/additem")
    public ResponseEntity addAdminItem(@RequestBody ItemDto.ItemAddDto itemDto) {
        ItemDto.Default savedItemDto = this.itemService.addItem(itemDto);
        DefaultItemResource defaultItemResource = new DefaultItemResource(savedItemDto);
        URI uri = linkTo(ItemController.class).slash(savedItemDto.getId()).toUri();
        defaultItemResource.add(linkTo(methodOn(AdminMyPageController.class).modifyItem(savedItemDto)).withRel("modify-item"));
        defaultItemResource.add(linkTo(methodOn(AdminMyPageController.class).deleteItem(savedItemDto.getId())).withRel("delete-item"));
        return ResponseEntity.created(uri).body(defaultItemResource);
    }


    @PutMapping("/items")
    public ResponseEntity modifyItem(@RequestBody ItemDto.Default itemDto) {
        itemService.modifyItem(itemDto);
        return ResponseEntity.ok("상품이 수정되었습니다.");
    }


    @DeleteMapping("/items/{id}")
    public ResponseEntity deleteItem(@PathVariable Long id) {
        Item item = itemService.findById(id).orElseThrow(() -> new NullPointerException("해당 상품은 존재하지 않습니다."));
        ItemDto.Default itemDto = itemService.deleteItem(item);
        return ResponseEntity.ok(itemDto);

    }




}
