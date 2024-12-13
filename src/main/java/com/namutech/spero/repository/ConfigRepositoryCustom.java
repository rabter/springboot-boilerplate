package com.namutech.spero.repository;

import com.namutech.spero.dto.ConfigSearchConditionDTO;
import com.namutech.spero.entity.Config;
import com.namutech.spero.entity.QConfig;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ConfigRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<Config> findwithConditions(ConfigSearchConditionDTO condition) {
        return queryFactory
                .select(QConfig.config)
                .from(QConfig.config)
                .where(containConfigKey(condition)
                        , eqConfigValue(condition))
                .fetch();
    }
    private BooleanExpression eqConfigKey(ConfigSearchConditionDTO condition) {
        if(Objects.isNull(condition.getConfigKey())) { return null; }
        return QConfig.config.configKey.eq(condition.getConfigKey());
    }

    private BooleanExpression eqConfigValue(ConfigSearchConditionDTO condition) {
        if(Objects.isNull(condition.getConfigValue())) { return null; }
        return QConfig.config.configValue.eq(condition.getConfigValue());
    }

    private BooleanExpression containConfigKey(ConfigSearchConditionDTO condition) {
        if(Objects.isNull(condition.getConfigKey())) { return null; }
        return QConfig.config.configKey.contains(condition.getConfigKey());
    }
}
