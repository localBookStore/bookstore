package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemResource;
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

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/api/index/", produces = MediaTypes.HAL_JSON_VALUE+";charset=utf-8")
public class IndexController {

    private final ItemService itemService;

    @GetMapping("/image/")
    public ResponseEntity getPromotionalImage() {

        log.info("Index 홍보 이미지");

        List<ItemDto> itemDtoList = itemService.getRandomList(3);
        List<ItemResource> itemList = itemDtoList.stream().map(itemDto -> new ItemResource(itemDto))
                                                              .collect(Collectors.toList());

        return new ResponseEntity(itemList, HttpStatus.OK);
    }

    @GetMapping({"/thismonth/", "/wepickitem/"})
    public ResponseEntity getThisMonthList(){
        log.info("이달의 도서 보내기");

        List<ItemDto> list= itemService.getRandomList(12);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    /*
    hover 시 각 카테고리 번호별 item 정보 3개씩 총 30개 아이템 랜덤 조회 요청
    (
     주의사항 :
       HATEOAS가 맘대로 [ [{}, {}, {}], [{}, {}, {}], [{}, {}, {}], ...] json 구조를
       [ {}, {}, {}, {}, {}, {}, {}, {}, {}, ...] json 구조로 바꿔 버림. 원인 알 수 없음.
    )
    */
    @GetMapping(value = "/genre/")
    public ResponseEntity getRandomListByGenre() {

        List<ItemDto> itemDtoList = itemService.getRandomListByGenre();
        List<ItemResource> itemList = itemDtoList.stream().map(itemDto -> new ItemResource(itemDto))
                                                                       .collect(Collectors.toList());

        // 카테고리 번호별로 분류한 json 구조로 직렬화(selialize)
        List<List<ItemResource>> selializedList = new ArrayList<>();
        for(int i = 0; i < itemList.size(); i+=3) {
            selializedList.add(new ArrayList<>(itemList.subList(i, Math.min(i+3, itemList.size()))));
        }

        return ResponseEntity.ok(selializedList);
    }

    /*
    hover 시 나타난 팝업 창 내 genre 중 하나 버튼 click 시, 각 장르별 item 리스트 조회 요청
    */
    @GetMapping(value = "/genre/{category_id}/")
    public ResponseEntity getListByGenre(@PathVariable("category_id") Long category_id) {

        List<ItemDto> itemDtoList = itemService.getListByGenre(category_id);
        List<ItemResource> itemList = itemDtoList.stream().map(itemDto -> new ItemResource(itemDto))
                                                                    .collect(Collectors.toList());

        return ResponseEntity.ok(itemList);
    }

    @GetMapping("/newitems/")
    public ResponseEntity getNewItems() {
        List<Item> newItems = this.itemService.getNewItems();
        List<ItemDto> itemDtos = newItems.stream().map(item -> ItemDto.of(item)).collect(Collectors.toList());
        List<ItemResource> itemResources = itemDtos.stream().map(itemDto -> new ItemResource(itemDto))
                                                                    .collect(Collectors.toList());
        CollectionModel<ItemResource> collectionModel = CollectionModel.of(itemResources);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/bestitems/")
    public ResponseEntity getBestItems() {
        List<Item> items = this.itemService.bestItems();
        List<ItemDto> itemDtos = items.stream().map(item -> ItemDto.of(item)).collect(Collectors.toList());
        List<ItemResource> itemResources = itemDtos.stream().map(itemDto -> new ItemResource(itemDto))
                                                                    .collect(Collectors.toList());
        CollectionModel<ItemResource> collectionModel = CollectionModel.of(itemResources);
        return ResponseEntity.ok(collectionModel);
    }
}
