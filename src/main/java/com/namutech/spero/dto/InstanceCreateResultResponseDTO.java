package com.namutech.spero.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstanceCreateResultResponseDTO {

    private String instanceId;

    @Builder
    public InstanceCreateResultResponseDTO(String instanceId) {
        this.instanceId = instanceId;
    }
}
