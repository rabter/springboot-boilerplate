package com.namutech.spero.common.service;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
public abstract class GenericService<T, Q extends EntityPath<T>> {

    @Autowired
    private JPAQueryFactory queryFactory;

    public Page<T> findAll(Pageable pageable, Q qClass, Function<Q, BooleanExpression> predicateBuilder) {
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
