package com.webservice.bookstore.domain.entity.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

   @Query(value = "select * from Item order by rand() limit :cnt",nativeQuery = true)
   List<Item> getThisMonthbooks(@Param("cnt") int cnt);

   @Query(value = "select * from Item where category_id = :category_id order by rand() limit 3",nativeQuery = true)
   List<Item> getRandomListByGenre(@Param("category_id") Long category_id);
}
