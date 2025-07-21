package com.namutech.spero.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.namutech.spero.resource.common.request.ResourceRequest;
import com.namutech.spero.resource.context.ResourceAttribute;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstanceCreateRequestDTO implements ResourceRequest {

    private String cloudId;
    private String vendor;
    private String instanceType;
    private String imageId;
    private String keyPairName;
    private String securityGroupId;

    // CSP 마다 다를 수 있는 필드들
    private Map<ResourceAttribute, Object> vendorSpecificData;

    @Builder
    public InstanceCreateRequestDTO(String cloudId, String vendor, String instanceType, String imageId, String keyPairName, String securityGroupId, Map<ResourceAttribute, Object> vendorSpecificData) {
        this.cloudId = cloudId;
        this.vendor = vendor;
        this.instanceType = instanceType;
        this.imageId = imageId;
        this.keyPairName = keyPairName;
        this.securityGroupId = securityGroupId;
        this.vendorSpecificData = vendorSpecificData;
    }

    @Override
    public String getCloudId() {
        return this.cloudId;
    }

}
