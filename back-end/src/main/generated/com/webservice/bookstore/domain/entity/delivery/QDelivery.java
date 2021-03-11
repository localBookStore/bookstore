package com.webservice.bookstore.domain.entity.delivery;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDelivery is a Querydsl query type for Delivery
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDelivery extends EntityPathBase<Delivery> {

    private static final long serialVersionUID = 495414423L;

    public static final QDelivery delivery = new QDelivery("delivery");

    public final StringPath address = createString("address");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<DeliveryEnum> status = createEnum("status", DeliveryEnum.class);

    public QDelivery(String variable) {
        super(Delivery.class, forVariable(variable));
    }

    public QDelivery(Path<? extends Delivery> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDelivery(PathMetadata metadata) {
        super(Delivery.class, metadata);
    }

}

