package com.webservice.bookstore.domain.entity.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {

    @Query("select i from Item i join fetch i.category ic")
    List<Item> findAll();

    @Query(value = "select * from item order by rand() limit :cnt",nativeQuery = true)
    List<Item> getThisMonthbooks(@Param("cnt") int cnt);

    @Query(value = "select * from (select I.*, row_number() over(partition by category_id order by rand()) rn from item I) I where rn <= 3", nativeQuery = true)
    List<Item> getRandomListByGenre();

    List<Item> findByCategoryId(Long id);


    @Query(value = "select * from item i order by i.view_count desc limit 30", nativeQuery = true)
    List<Item> getBestItems();

    @Query("select i from Item i join fetch i.category ic where i.id = :id")
    Optional<Item> findById(@Param("id") Long id);

//    List<Item> findByIdIn(List<Long> itemIdList);

    @Query(value = "select * from item i order by i.publication_date desc limit 30", nativeQuery = true)
    List<Item> getNewItems();


    @Modifying(clearAutomatically = true)
    @Query("delete from Item i where i.id in :ids")
    void deleteIn(@Param("ids") List<Long> ids);

    @Query("select i from Item i where i.id in :ids")
    List<Item> selectIn(@Param("ids") List<Long> ids);



//    @Modifying(clearAutomatically = true)
//    @Query("update Item i set i.viewCount = i.viewCount + 1 where i.id = :id")
//    int improveViewCount(@Param("id") Long id);
}
