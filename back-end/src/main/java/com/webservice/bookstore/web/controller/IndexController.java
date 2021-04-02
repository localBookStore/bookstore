package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.web.resource.DefaultItemResource;
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
public class IndexController {

    private final ItemService itemService;

    @GetMapping("/image/")
    public ResponseEntity<List<ItemDto.Default>> getPromotionalImage() {

        log.info("Index 홍보 이미지");

        List<ItemDto.Default> itemDtoList = itemService.getRandomList(3);

        return new ResponseEntity<>(itemDtoList, HttpStatus.OK);
    }

    @GetMapping({"/thismonth/","/wepickitem/"})
    public ResponseEntity<List<ItemDto.Default>> getThisMonthList(){
        log.info("이달의 도서 보내기");

        List<ItemDto.Default> list= itemService.getRandomList(12);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }


    /*
    hover 시 각 카테고리 번호별 item 정보 3개씩 총 30개 아이템 랜덤 조회 요청
    (
     주의사항 :
       HATEOAS가 맘대로 [ [{}, {}, {}], [{}, {}, {}], [{}, {}, {}], ...] json 구조를
       [ {}, {}, {}, {}, {}, {}, {}, {}, {}, ...] json 구조로 바꿔 버림. 원인 알 수 없음.
    )
    */
    @GetMapping(value = "/genre/", produces = MediaTypes.HAL_JSON_VALUE+";charset=utf-8")
    public ResponseEntity getRandomListByGenre() {

        List<ItemDto.Default> itemDtoList = itemService.getRandomListByGenre();

        List<DefaultItemResource> emList = itemDtoList.stream()
                .map(itemDto -> new DefaultItemResource(itemDto,
                        linkTo(methodOn(ItemController.class).getItem(itemDto.getId())).withSelfRel()))
                .collect(Collectors.toList());

        // 카테고리 번호별로 분류한 json 구조로 직렬화(selialize)
        List<List<DefaultItemResource>> first = new ArrayList<>();
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

        List<ItemDto.Default> itemList = itemService.getListByGenre(category_id);

        List<DefaultItemResource> emList = itemList.stream()
                .map(itemDto -> new DefaultItemResource(itemDto,
                        linkTo(methodOn(IndexController.class).getListByGenre(itemDto.getId())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<DefaultItemResource> collectionModel = CollectionModel.of(emList);

        return new ResponseEntity(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/newitems/")
    public ResponseEntity getNewItems() {
        List<Item> newItems = this.itemService.getNewItems();
        List<ItemDto.Default> itemDtos = newItems.stream().map(item -> ItemDto.Default.of(item)).collect(Collectors.toList());
        List<DefaultItemResource> defaultItemResources = itemDtos.stream().map(itemDto -> new DefaultItemResource(itemDto))
                                                                             .collect(Collectors.toList());
        CollectionModel<DefaultItemResource> collectionModel = CollectionModel.of(defaultItemResources);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/bestitems/")
    public ResponseEntity getBestItems() {
        List<Item> items = this.itemService.bestItems();
        List<ItemDto.Default> itemDtos = items.stream().map(item -> ItemDto.Default.of(item)).collect(Collectors.toList());
        List<DefaultItemResource> defaultItemResources = itemDtos.stream().map(itemDto -> new DefaultItemResource(itemDto))
                                                                            .collect(Collectors.toList());
        CollectionModel<DefaultItemResource> collectionModel = CollectionModel.of(defaultItemResources);
        return ResponseEntity.ok(collectionModel);
    }
}
