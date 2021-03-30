package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.cart.CartRepository;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemRepository;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.domain.entity.order.Orders;
import com.webservice.bookstore.domain.entity.order.OrdersRepository;
import com.webservice.bookstore.domain.entity.orderItem.OrderItem;
import com.webservice.bookstore.web.dto.CartDto;
import com.webservice.bookstore.web.dto.MemberDto;
import com.webservice.bookstore.web.dto.OrderItemDto;
import com.webservice.bookstore.web.dto.OrdersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdersService {

    private final CartRepository cartRepository;
    private final OrdersRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /*
    주문 생성
    */
    // OrderItemDto 리스트 내 item_id 리스트 반환
    private List<Long> getItemIdList(List<OrderItemDto> orderItemDtoList) {
        List<Long> itemIdList = new ArrayList<>();
        for(OrderItemDto dto : orderItemDtoList) {
//            itemIdList.add(dto.getItem_id());
            itemIdList.add(dto.getItemDto().getId());
        }
        return itemIdList;
    }

    private List<Long> getCartIdList(List<CartDto> cartDtoList) {
        List<Long> cartIdList = new ArrayList<>();
        for(CartDto dto : cartDtoList) {
            cartIdList.add(dto.getId());
        }
        return cartIdList;
    }

    @Transactional
    public void addOrder(List<CartDto> cartDtoList, MemberDto memberDto, List<OrderItemDto> orderItemDtoList) {
        // 먼저 item_id 필드 기준으로 리스트 정렬 (오름차순)
        orderItemDtoList = orderItemDtoList.stream()
//                .sorted(Comparator.comparing(OrderItemDto::getItem_id))
                .sorted(Comparator.comparing(orderItemDto -> orderItemDto.getItemDto().getId()))
                .collect(Collectors.toList());

        // Member, Item 엔티티 조회 (자동으로 id 기준으로 오름차순을 조회함)
        Member member       = memberRepository.getOne(memberDto.getId());
        member.setAddress(memberDto.getAddress());
        List<Item> itemList = itemRepository.findByIdIn(getItemIdList(orderItemDtoList));

        // 주문상품 생성
        List<OrderItem> orderItemList = OrderItem.createOrderItem(itemList, orderItemDtoList);

        // 주문 생성
        Orders orders = Orders.createOrder(member, orderItemList);

        // 주문 저장
        orderRepository.save(orders);

        // 장바구니 아이템 삭제
        cartRepository.deleteAllByIdInQuery(getCartIdList(cartDtoList));

    }

    public List<OrdersDto> findOrders(MemberDto memberDto) {
        List<Orders> orderEntityList = orderRepository.findByMemberId(memberDto.getId());
        List<OrdersDto> ordersDtoList = new ArrayList<>();
        orderEntityList.stream().forEach(orders -> ordersDtoList.add(OrdersDto.of(orders)));

        return ordersDtoList;
    }

    @Transactional
    public void cancelOrder(MemberDto memberDto, OrdersDto ordersDto) {

        Orders order = orderRepository.getOne(ordersDto.getId());
        order.cancel();
    }
}
