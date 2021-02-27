package com.webservice.bookstore.repository;

import com.webservice.bookstore.domain.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {

}
