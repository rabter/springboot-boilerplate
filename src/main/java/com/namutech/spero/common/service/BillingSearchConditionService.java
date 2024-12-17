package com.namutech.spero.common.service;

import com.namutech.spero.dto.BillingSearchConditionDTO;
import com.namutech.spero.entity.QBilling;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.Objects;

public class BillingSearchConditionService {

    private BooleanExpression eqCspType(BillingSearchConditionDTO condition) {
        if (Objects.isNull(condition.getCspType())) {
            return null;
        }
        return QBilling.billing.cspType.eq(condition.getCspType());
    }

    public BooleanExpression buildPredicate(BillingSearchConditionDTO condition) {
        BooleanExpression predicate = eqCspType(condition);

        if (Objects.nonNull(predicate)) {
            return predicate.and(eqCspType(condition));
        }
        return null;
    }
}
