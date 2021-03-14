package com.webservice.bookstore.domain.entity.item;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.webservice.bookstore.domain.entity.item.QItem.item;

@RequiredArgsConstructor
@Repository
public class ItemQueryRespository {

    private final JPAQueryFactory jpaQueryFactory;


    public List<Item> findDynamicBooks(ItemSearch itemSearch) {
        List<Item> items = jpaQueryFactory
                .select(item)
                .from(item)
                .where(nameLike(itemSearch.getName()),
                        authorLike(itemSearch.getAuthor()))
                .fetch();
        return items;

    }

    private BooleanExpression authorLike(String author) {
        if(author == null) {
            return null;
        }
        return item.author.contains(author);
    }

    private BooleanExpression nameLike(String bookName) {
        if(bookName == null) {
            return null;
        }
        return item.name.contains(bookName);
    }
}
