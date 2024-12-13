package com.namutech.spero.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfigSearchConditionDTO {
    private String configKey;
    private String configValue;

    @Builder
    public ConfigSearchConditionDTO(String configKey, String configValue, String description, String configGroup, String configGroupDescription) {
        this.configKey = configKey;
        this.configValue = configValue;
    }

}
