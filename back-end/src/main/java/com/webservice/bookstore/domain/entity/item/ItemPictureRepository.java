package com.webservice.bookstore.domain.entity.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPictureRepository extends JpaRepository<ItemPicture, Long> {
    ItemPicture save(ItemPicture itemPicture);
    List<ItemPicture> findAllByItemId(Long itemId);
}
