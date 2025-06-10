package com.namutech.spero.common.service;

import com.namutech.spero.common.dto.BaseSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Service
public abstract class GenericService<T, Q extends EntityPath<T>, C extends BaseSearchDTO> {

    @Autowired
    private JPAQueryFactory queryFactory;

    protected abstract PathBuilder<T> getPathBuilder();
    protected abstract BooleanBuilder buildPredicate(C condition);

    public Pageable getPageable(C condition) {
        return PageRequest.of(condition.getPageNumber() - 1, condition.getPageSize());
    }

    public Page<T> findAll(C condition, Q qClass, Function<Q, BooleanBuilder> predicateBuilder) {
        Pageable pageable = getPageable(condition);

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
}
