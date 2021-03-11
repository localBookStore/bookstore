package com.webservice.bookstore.domain.entity.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrders is a Querydsl query type for Orders
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOrders extends EntityPathBase<Orders> {

    private static final long serialVersionUID = -120234948L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrders orders = new QOrders("orders");

    public final com.webservice.bookstore.domain.entity.delivery.QDelivery delivery;

    public final NumberPath<Integer> deliveryCharge = createNumber("deliveryCharge", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.webservice.bookstore.domain.entity.member.QMember member;

    public final ListPath<com.webservice.bookstore.domain.entity.orderItem.OrderItem, com.webservice.bookstore.domain.entity.orderItem.QOrderItem> orderItems = this.<com.webservice.bookstore.domain.entity.orderItem.OrderItem, com.webservice.bookstore.domain.entity.orderItem.QOrderItem>createList("orderItems", com.webservice.bookstore.domain.entity.orderItem.OrderItem.class, com.webservice.bookstore.domain.entity.orderItem.QOrderItem.class, PathInits.DIRECT2);

    public final EnumPath<OrdersEnum> status = createEnum("status", OrdersEnum.class);

    public QOrders(String variable) {
        this(Orders.class, forVariable(variable), INITS);
    }

    public QOrders(Path<? extends Orders> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrders(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrders(PathMetadata metadata, PathInits inits) {
        this(Orders.class, metadata, inits);
    }

    public QOrders(Class<? extends Orders> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.delivery = inits.isInitialized("delivery") ? new com.webservice.bookstore.domain.entity.delivery.QDelivery(forProperty("delivery")) : null;
        this.member = inits.isInitialized("member") ? new com.webservice.bookstore.domain.entity.member.QMember(forProperty("member")) : null;
    }

}

