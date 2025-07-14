package com.namutech.spero.common.dto;

import com.namutech.spero.dto.InstanceCreateRequestDTO;
import com.namutech.spero.entity.Cloud;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ResourceContext {
    // 내부 DB 비즈니스 처리용
    private final Cloud cloud;
    private InstanceCreateRequestDTO requestDto; // 원본 요청 DTO

    private Map<String, Object> vendorSpecificData; // 벤더별 데이터

    public <T> T getVendorAttr(String key, Class<T> clazz) {
        Object value = vendorSpecificData.get(key);
        if (value == null) throw new IllegalArgumentException("Missing vendor-specific attribute: " + key);
        return clazz.cast(value);
    }

    public void putVendorAttr(String key, Object value) {
        vendorSpecificData.put(key, value);
    }

}
