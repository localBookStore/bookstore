package com.webservice.bookstore.domain.entity.item;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {


   @Query(value = "select * from Item order by rand() limit :cnt",nativeQuery = true)
   List<Item> getThisMonthbooks(@Param("cnt") int cnt);

    @Query(value = "select * from (select I.*, row_number() over(partition by category_id order by rand()) rn from item I) I where rn <= 3", nativeQuery = true)
   List<Item> getRandomListByGenre();

   @EntityGraph(value = "Item.category")
   List<Item> findAllByCategoryId(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Item i set i.viewCount = i.viewCount + 1 where i.id = :id")
    int improveViewCount(@Param("id") Long id);

    @Query("select i from Item i order by i.viewCount desc")
    List<Item> bestItems();


}
