package com.namutech.spero.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstanceCreateRequestDTO {

    private String vendor;
    private String instanceType;
    private String imageId;
    private String keyPairName;
    private String securityGroupId;

    @Builder
    public InstanceCreateRequestDTO(String vendor, String instanceType, String imageId, String keyPairName, String securityGroupId) {
        this.vendor = vendor;
        this.instanceType = instanceType;
        this.imageId = imageId;
        this.keyPairName = keyPairName;
        this.securityGroupId = securityGroupId;
    }
}
