package com.webservice.bookstore.domain.entity.review;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    //사용자가 주문 해당아이템 주문한적있는지 확인
    @Query(value = "select count(*) from member m join orders s on m.id = s.member_id "+
            "join order_item o on o.id = s.id " +
            "join item i on o.item_id = i.id " +
            "where m.id = :member_id and i.id =:item_id",nativeQuery = true)
    Long hasOrder(@Param("member_id") Long memberId ,
                     @Param("item_id") Long itemId);


    @EntityGraph(attributePaths = {"item","member"},type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "select r from Review r where r.item.id =:item_id")
    List<Review> getItemReviewList(@Param("item_id")Long itemId);

}
