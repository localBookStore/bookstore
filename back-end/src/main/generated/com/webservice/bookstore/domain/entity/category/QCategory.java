package com.webservice.bookstore.domain.entity.category;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = -1289029673L;

    public static final QCategory category = new QCategory("category");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.webservice.bookstore.domain.entity.item.Item, com.webservice.bookstore.domain.entity.item.QItem> items = this.<com.webservice.bookstore.domain.entity.item.Item, com.webservice.bookstore.domain.entity.item.QItem>createList("items", com.webservice.bookstore.domain.entity.item.Item.class, com.webservice.bookstore.domain.entity.item.QItem.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

