package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.cart.Cart;
import com.webservice.bookstore.domain.entity.cart.CartRepository;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemRepository;
import com.webservice.bookstore.web.dto.CartDto;
import com.webservice.bookstore.web.dto.ItemDto;
import com.webservice.bookstore.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    /*
    장바구니 목록 조회 서비스 단계
    */
    public List<CartDto> findByMemberId(Long id) {

        List<Cart> cartEntityList = cartRepository.findByMemberId(id);

        List<CartDto> cartDtoList = new ArrayList<>();
        for(Cart cartEntity : cartEntityList) {
            CartDto cartDto = CartDto.of(cartEntity);
            cartDtoList.add(cartDto);
        }
        return cartDtoList;
    }

    /*
    장바구니 아이템 추가 요청 핸들러
    */
    @Transactional
    public CartDto addCartEntity(CartDto cartDto) {

        Item item = itemRepository.getOne(cartDto.getItem_id());
        cartDto.setPrice(item.getPrice());

        Cart cart = cartDto.toEntity();
        Cart savedCart = cartRepository.save(cart);

        return CartDto.of(savedCart);
    }

    /*
    장바구니 아이템 수량 업데이트 서비스 단계
    */
    @Transactional
    public void updateQuantity(Long id, int quantity) {

        Cart cartEntity = cartRepository.getOne(id);

        cartEntity.updateQuantity(quantity); // 장바구니 아이템 수량 업데이트 메소드 호출
    }

    /*
    장바구니 아이템 삭제 서비스 단계
    select 데이터가 없으면 EmptyResultDataAccessException 예외 발생
    */
    @Transactional
    public void deleteCartItem(Long id) throws EmptyResultDataAccessException {
        cartRepository.deleteById(id);
    }

}
