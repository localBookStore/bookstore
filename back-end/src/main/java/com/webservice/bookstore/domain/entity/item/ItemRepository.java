package com.webservice.bookstore.domain.entity.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item,Long> {

    @Query("update Item i set i.viewCount = i.viewCount + 1 where i.id = :id")
    int improveViewCount(@Param("id") int id);

}
