package com.webservice.bookstore.domain.entity.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Item i set i.viewCount = i.viewCount + 1 where i.id = :id")
    int improveViewCount(@Param("id") Long id);

    @Query("select i from Item i order by i.viewCount desc")
    List<Item> bestItems();

}
