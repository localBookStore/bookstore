package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.coupon.Coupon;
import com.webservice.bookstore.domain.entity.coupon.CouponRepository;
import com.webservice.bookstore.domain.entity.cart.CartRepository;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemRepository;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.domain.entity.order.Orders;
import com.webservice.bookstore.domain.entity.order.OrdersRepository;
import com.webservice.bookstore.domain.entity.orderItem.OrderItem;
import com.webservice.bookstore.web.dto.*;
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
    private final CouponRepository couponRepository;

    /*
    주문 생성
    */
    // OrderItemDto 리스트 내 item_id 리스트 반환
    private List<Long> getItemIdList(List<OrderItemDto.Default> orderItemDtoList) {
        List<Long> itemIdList = new ArrayList<>();
        orderItemDtoList.stream().forEach(dto -> itemIdList.add(dto.getItemDto().getId()));
        return itemIdList;
    }

    private List<Long> getCartIdList(List<CartDto.Default> cartDtoList) {
        List<Long> cartIdList = new ArrayList<>();
        cartDtoList.stream().forEach(dto -> cartIdList.add(dto.getId()));
        return cartIdList;
    }

    @Transactional
    public void addOrder(MemberDto.Default memberDto,
                         CouponDto couponDto,
                         List<CartDto.Default> cartDtoList,
                         List<OrderItemDto.Default> orderItemDtoList) {
        // 먼저 item_id 필드 기준으로 리스트 정렬 (오름차순)
        orderItemDtoList = orderItemDtoList.stream()
                                           .sorted(Comparator.comparing(orderItemDto -> orderItemDto.getItemDto().getId()))
                                           .collect(Collectors.toList());

        // Member, Item 엔티티 조회 (자동으로 id 기준으로 오름차순을 조회함)
        Member member       = memberRepository.getOne(memberDto.getId());
        member.setAddress(memberDto.getAddress());
        List<Item> itemList = itemRepository.findByIdIn(getItemIdList(orderItemDtoList));

        Coupon coupon = null;
        if(couponDto != null) {
            coupon = couponRepository.findById(couponDto.getId()).get();
            coupon.isUsed(true);
            member.addCoupon(coupon);
        }

        // 주문상품 생성
        List<OrderItem> orderItemList = OrderItem.createOrderItem(itemList, orderItemDtoList);

        // 주문 생성
        Orders orders = Orders.createOrder(member, coupon, orderItemList);

        // 주문 저장
        orderRepository.save(orders);

        // 장바구니 아이템 삭제
        cartRepository.deleteAllByIdInQuery(getCartIdList(cartDtoList));

    }

    public List<OrdersDto.Default> findOrders(MemberDto.Default memberDto) {

        List<Orders> orderEntityList = orderRepository.getAllByMemberId(memberDto.getId());
        List<OrdersDto.Default> ordersDtoList = new ArrayList<>();
        orderEntityList.stream().forEach(orders -> ordersDtoList.add(OrdersDto.Default.of(orders)));

        return ordersDtoList;
    }

    @Transactional
    public void cancelOrder(MemberDto.Default memberDto, OrdersDto.Default ordersDto) {

        Orders order = orderRepository.getOne(ordersDto.getId());
        order.cancel();
    }
}
