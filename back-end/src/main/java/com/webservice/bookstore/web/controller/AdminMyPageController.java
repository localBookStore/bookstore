package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.config.security.auth.CustomUserDetails;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.exception.UnauthorizedException;
import com.webservice.bookstore.service.MemberService;
import com.webservice.bookstore.service.OrdersService;
import com.webservice.bookstore.web.dto.MemberDto;
import com.webservice.bookstore.web.dto.OrdersDto;
import com.webservice.bookstore.web.resource.DefaultItemResource;
import com.webservice.bookstore.domain.entity.item.ItemSearch;
import com.webservice.bookstore.service.ItemService;
import com.webservice.bookstore.web.dto.DeleteRequestDto;
import com.webservice.bookstore.web.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/api/admin", produces = MediaTypes.HAL_JSON_VALUE+";charset=utf-8")
public class AdminMyPageController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrdersService orderService;

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


    @PostMapping(value = "/items/additem")
    public ResponseEntity addAdminItem(@RequestBody ItemDto.ItemAddDto itemDto) {
        ItemDto.Default savedItemDto = this.itemService.addItem(itemDto);
        DefaultItemResource defaultItemResource = new DefaultItemResource(savedItemDto);
        URI uri = linkTo(ItemController.class).slash(savedItemDto.getId()).toUri();
        defaultItemResource.add(linkTo(methodOn(AdminMyPageController.class).modifyItem(savedItemDto)).withRel("modify-item"));
        defaultItemResource.add(linkTo(methodOn(AdminMyPageController.class).deleteItems(null)).withRel("delete-item"));
        return ResponseEntity.created(uri).body(defaultItemResource);
    }


    @PutMapping("/items")
    public ResponseEntity modifyItem(@RequestBody ItemDto.Default itemDto) {
        itemService.modifyItem(itemDto);
        return ResponseEntity.ok("상품이 수정되었습니다.");
    }


    @DeleteMapping("/items")
    public ResponseEntity deleteItems(@RequestBody List<Long> ids) {
        List<ItemDto.Default> remainItems = itemService.deleteItem(ids);
        List<DefaultItemResource> itemLinkResources = remainItems.stream().map(itemDto -> new DefaultItemResource(itemDto)).collect(Collectors.toList());
        return ResponseEntity.ok(itemLinkResources);
    }

    /*
    관리자 페이지 각 회원 리스트 조회
    */
    @GetMapping("/members")
    public ResponseEntity searchMembers(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        verifyAuthentication(customUserDetails);

        List<MemberDto.Default> memberDtoList = memberService.findAllMembers();

        return ResponseEntity.ok(memberDtoList);
    }

    /*
    관리자 페이지 각 회원 주문 리스트 (구매 내역) 조회
    */
    @GetMapping("/members/{member_id}/orders")
    public ResponseEntity getOrderList(@PathVariable(value = "member_id") Long member_id,
                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        verifyAuthentication(customUserDetails);

        MemberDto.Default memberDto = MemberDto.Default.builder().id(member_id).build();

        List<OrdersDto.Default> orderDtoList = orderService.findOrders(memberDto);

        List<OrdersDto.Response> orderList = new ArrayList<>();
        orderDtoList.stream().forEach(orderDto -> orderList.add(orderDto.toResponse()));

        return ResponseEntity.ok(orderList);
    }

    /*
    관리자 페이지 주문 취소 요청
    */
    @PatchMapping("/orders/cancel/{order_id}")
    public ResponseEntity cancelOrder(@RequestBody @PathVariable(value = "order_id") Long orders_id,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        verifyAuthentication(customUserDetails);

        OrdersDto.Default ordersDto = OrdersDto.Default.builder()
                                                       .id(orders_id)
                                                       .member_id(customUserDetails.getMember().getId())
                                                       .build();

        List<OrdersDto.Default> orderDtoList = orderService.cancelOrder(ordersDto);

        List<OrdersDto.Response> orderList = new ArrayList<>();
        orderDtoList.stream().forEach(orderDto -> orderList.add(orderDto.toResponse()));

        return ResponseEntity.ok(orderList);
    }

    private void verifyAuthentication(CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            throw new UnauthorizedException("인증 오류가 발생했습니다.");
        } else if(!customUserDetails.isEnabled()) {
            throw new UnauthorizedException("계정이 잠겨있습니다. 관리자에게 문의해주시길 바랍니다.");
        }
    }


}
