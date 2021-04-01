package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.cart.Cart;
import com.webservice.bookstore.domain.entity.cart.CartRepository;
import com.webservice.bookstore.domain.entity.item.Item;
import com.webservice.bookstore.domain.entity.item.ItemRepository;
import com.webservice.bookstore.web.dto.CartDto;
import com.webservice.bookstore.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
    public List<CartDto.Default> findByMemberId(Long id) {

        List<CartDto.Default> cartDtoList = setDtoListOfEntityList(cartRepository.findByMemberId(id));

        return cartDtoList;
    }

    /*
    장바구니 아이템 추가 요청 핸들러
    */
    @Transactional
    public void addCartEntity(CartDto.Default cartDto) {

        isExistInCart(cartDto);

        Item item = itemRepository.findById(cartDto.getItemDto().getId()).orElseThrow(() -> new EntityNotFoundException());
        cartDto.setPrice(item.getPrice());

        Cart cart = cartDto.toEntity();
        cartRepository.save(cart);

    }
    private void isExistInCart(CartDto.Default cartDto) {
        Optional<Cart> optionalCart
                = cartRepository.findByMemberIdAndItemId(cartDto.getMember_id(), cartDto.getItemDto().getId());
        if(optionalCart.isPresent()) {
            throw new RuntimeException();
        }
    }

    /*
    장바구니 아이템 수량 업데이트 서비스 단계
    */
    @Transactional
    public void updateQuantity(Long id, int orderCount) {

        Cart cartEntity = cartRepository.getOne(id);

        cartEntity.updateQuantity(orderCount); // 장바구니 아이템 수량 업데이트 메소드 호출
    }

    /*
    장바구니 아이템 삭제 서비스 단계
    select 데이터가 없으면 EmptyResultDataAccessException 예외 발생
    */
    @Transactional
    public List<CartDto.Default> deleteCartItem(MemberDto.Default memberDto,
                                                List<CartDto.Default> cartDtoList) throws EmptyResultDataAccessException {

        cartRepository.deleteAllByIdInQuery(getCartIdList(cartDtoList));

        List<Cart> cartEntityList = cartRepository.findByMemberId(memberDto.getId());
        cartDtoList = setDtoListOfEntityList(cartEntityList);

        return cartDtoList;
    }
    // Cart Entity 리스트 내 cart_id만 리스트로 반환
    private List<Long> getCartIdList(List<CartDto.Default> cartDtoList) {
        List<Long> cartIdList = new ArrayList<>();
        for(CartDto.Default dto : cartDtoList) {
            cartIdList.add(dto.getId());
        }
        return cartIdList;
    }
    // Lambda는 'effectively final'(값이 재정의되지 않는 변수) 변수만 허용 가능하도록 하여 메소드로 분리
    private static List<CartDto.Default> setDtoListOfEntityList(List<Cart> cartEntityList) {
        List<CartDto.Default> cartDtoList = new ArrayList<>();
        cartEntityList.stream().forEach(cart -> cartDtoList.add(CartDto.Default.of(cart)));
        return cartDtoList;
    }
}
