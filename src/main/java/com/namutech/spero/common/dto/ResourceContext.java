package com.namutech.spero.common.dto;

import com.namutech.spero.entity.Cloud;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResourceContext {
    // 내부 DB 비즈니스 처리용
    private final Cloud cloud;
}
