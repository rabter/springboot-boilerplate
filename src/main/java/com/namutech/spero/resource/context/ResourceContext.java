package com.namutech.spero.resource.context;

import com.namutech.spero.common.dto.ResourceRequest;
import com.namutech.spero.entity.Cloud;
import lombok.Builder;
import lombok.Getter;

import java.util.EnumMap;
import java.util.Map;

@Getter
public class ResourceContext<T extends ResourceRequest> {
    // 내부 DB 비즈니스 처리용
    private final Cloud cloud;
    private final T requestDto;

    private final Map<ResourceAttribute, Object> vendorSpecificData; // 벤더별 속성 (ex. REGION, ZONE 등)

    @Builder
    public ResourceContext(Cloud cloud, T requestDto, Map<ResourceAttribute, Object> vendorSpecificData) {
        this.cloud = cloud;
        this.requestDto = requestDto;
        this.vendorSpecificData = vendorSpecificData != null ? vendorSpecificData : new EnumMap<>(ResourceAttribute.class);
    }

    public <U> U getVendorAttr(ResourceAttribute key, Class<U> clazz) {
        Object value = vendorSpecificData.get(key);
        if (value == null) throw new IllegalArgumentException("Missing vendor-specific attribute: " + key.name());
        return clazz.cast(value);
    }

    public void putVendorAttr(ResourceAttribute key, Object value) {
        vendorSpecificData.put(key, value);
    }

}
