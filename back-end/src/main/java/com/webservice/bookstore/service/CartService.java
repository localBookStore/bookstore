package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.cart.Cart;
import com.webservice.bookstore.domain.entity.cart.CartRepository;
import com.webservice.bookstore.web.dto.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    /*
    장바구니 목록 조회 서비스 단계
    */
    public List<CartDto> findByMemberId(Long id) {
        List<Cart> cartEntityList = cartRepository.findByMemberId(id);
        List<CartDto> cartDtoList = new ArrayList<>();
        for(Cart cartEntity : cartEntityList) {
            CartDto cartDto = CartDto.builder()
                    .id(cartEntity.getId())
                    .member_id(cartEntity.getMember().getId())
                    .item_id(cartEntity.getItem().getId())
                    .price(cartEntity.getPrice())
                    .quantity(cartEntity.getQuantity())
                    .build();

            cartDtoList.add(cartDto);
        }
        return cartDtoList;
    }
}
