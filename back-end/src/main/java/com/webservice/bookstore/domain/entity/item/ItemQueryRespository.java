package com.webservice.bookstore.domain.entity.item;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.webservice.bookstore.domain.entity.item.QItem.item;

@RequiredArgsConstructor
@Repository
public class ItemQueryRespository {

    private final JPAQueryFactory jpaQueryFactory;


    public Page<Item> findDynamicBooks(ItemSearch itemSearch, Pageable pageable) {
        QueryResults<Item> itemQueryResults = jpaQueryFactory
                .select(item)
                .from(item)
                .where(eqBookName(itemSearch.getName()),
                        eqAuthor(itemSearch.getAuthor()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(itemQueryResults.getResults(), pageable , itemQueryResults.getTotal());

    }

    private BooleanExpression eqAuthor(String author) {
        if(author == null) {
            return null;
        }
        return item.author.eq(author);
    }

    private BooleanExpression eqBookName(String bookName) {
        if(bookName == null) {
            return null;
        }
        return item.name.eq(bookName);
    }
}
