package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemLinkResource;
import com.webservice.bookstore.service.ItemService;
import com.webservice.bookstore.web.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/api/index/", produces = MediaTypes.HAL_JSON_VALUE+";charset=utf-8")
@CrossOrigin(origins = {"http://localhost:3000"})
public class IndexController {

    private final ItemService itemServices;

    private final ItemService itemService;

    @GetMapping("/image/")
    public ResponseEntity<List<ItemDto>> getPromotionalImage() {

        log.info("Index 홍보 이미지");

        List<ItemDto> itemDtoList = itemServices.getRandomList(3);

        return new ResponseEntity<>(itemDtoList, HttpStatus.OK);
    }

    @GetMapping("/thismonth/")
    public ResponseEntity<List<ItemDto>> getThisMonthList(){
        log.info("이달의 도서 보내기");

        List<ItemDto> list= itemServices.getRandomList(12);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    public ResponseEntity<List<ItemDto>> getWePickItem(){
        log.info("우리의 PICK 보내기");
        return new ResponseEntity<>(itemServices.getRandomList(12) ,HttpStatus.OK);
    }

    /*
    hover 시 각 카테고리 번호별 item 정보 3개씩 총 30개 아이템 랜덤 조회 요청
    */
    @GetMapping(value = "/genre/", produces = MediaTypes.HAL_JSON_VALUE+";charset=utf-8")
    public ResponseEntity getRandomListByGenre() {

        List<ItemDto> itemDtoList = itemServices.getRandomListByGenre();

        List<ItemLinkResource> emList = itemDtoList.stream()
                .map(itemDto -> new ItemLinkResource(itemDto,
                        linkTo(methodOn(ItemController.class).getItem(itemDto.getId())).withSelfRel()))
                .collect(Collectors.toList());

        // 카테고리 번호별로 분류한 json 구조로 직렬화(selialize)
        List<List<ItemLinkResource>> first = new ArrayList<>();
        for(int i = 0; i < emList.size(); i+=3) {
            first.add(new ArrayList<>(emList.subList(i, Math.min(i+3, emList.size()))));
        }

        return new ResponseEntity<>(first, HttpStatus.OK);
    }

    /*
    hover 시 나타난 팝업 창 내 genre 중 하나 버튼 click 시, 각 장르별 item 리스트 조회 요청
    */
    @GetMapping(value = "/genre/{category_id}/", produces = MediaTypes.HAL_JSON_VALUE+";charset=utf-8")
    public ResponseEntity<CollectionModel> getListByGenre(@PathVariable("category_id") Long category_id) {

        List<ItemDto> itemList = itemServices.getListByGenre(category_id);

        List<ItemLinkResource> emList = itemList.stream()
                .map(itemDto -> new ItemLinkResource(itemDto,
                        linkTo(methodOn(IndexController.class).getListByGenre(itemDto.getId())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<ItemLinkResource> collectionModel = CollectionModel.of(emList);

        return new ResponseEntity(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/newitems")
    public ResponseEntity getNewItems() {
        List<Item> newItems = this.itemService.getNewItems();
        List<ItemDto> itemDtos = newItems.stream().map(item -> ItemDto.of(item)).collect(Collectors.toList());
        List<ItemLinkResource> itemLinkResources = itemDtos.stream().map(itemDto -> new ItemLinkResource(itemDto, linkTo(ItemController.class).slash(itemDto.getId()).withSelfRel()))
                .collect(Collectors.toList());
        CollectionModel<ItemLinkResource> collectionModel = CollectionModel.of(itemLinkResources);
        return ResponseEntity.ok(collectionModel);
    }
}
