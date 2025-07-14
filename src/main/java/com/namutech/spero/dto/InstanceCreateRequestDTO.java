package com.namutech.spero.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstanceCreateRequestDTO {

    private String vendor;
    private String instanceType;
    private String imageId;
    private String keyPairName;
    private String securityGroupId;

    // CSP 마다 다를 수 있는 필드들
    private Map<String, Object> vendorSpecificData;

    @Builder
    public InstanceCreateRequestDTO(String vendor, String instanceType, String imageId, String keyPairName, String securityGroupId, Map<String, Object> vendorSpecificData) {
        this.vendor = vendor;
        this.instanceType = instanceType;
        this.imageId = imageId;
        this.keyPairName = keyPairName;
        this.securityGroupId = securityGroupId;
        this.vendorSpecificData = vendorSpecificData;
    }
}
