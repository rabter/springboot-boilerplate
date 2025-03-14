package com.namutech.spero.common.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Service
public abstract class GenericService<T, Q extends EntityPath<T>> {

    @Autowired
    private JPAQueryFactory queryFactory;

    public BooleanBuilder buildPredicate(Path<T> qEntity, Object condition) {
        BooleanBuilder builder = new BooleanBuilder();

        Field[] fields = condition.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(condition);

                if (Objects.nonNull(value)) {
                    PathBuilder<T> path = new PathBuilder<>(qEntity.getType(), qEntity.getMetadata());
                    builder.and(path.get(field.getName()).eq(value));
                }
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("IllegalArgumentException");
            } finally {
                field.setAccessible(false);
            }
        }

        return builder;
    }

    public Page<T> findAll(Pageable pageable, Q qClass, Function<Q, BooleanBuilder> predicateBuilder) {
        List<T> results = queryFactory
                .selectFrom(qClass)
                .where(predicateBuilder.apply(qClass))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(Expressions.numberTemplate(Long.class, "count(*)"))
                .from(qClass)
                .where(predicateBuilder.apply(qClass))
                .fetchOne();

        long total = totalCount != null ? totalCount : 0L;
        return new PageImpl<>(results, pageable, total);
    }

    protected BooleanExpression addCondition(BooleanExpression base, BooleanExpression condition) {
        if (condition == null) {
            return base;
        }
        return base.and(condition);
    }
}
