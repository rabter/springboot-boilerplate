package com.namutech.spero.dto;

import com.namutech.spero.entity.Config;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfigDTO {

    private String configKey;
    private String configValue;
    private String description;
    private String configGroup;
    private String configGroupDescription;

    @Builder
    public ConfigDTO(String configKey, String configValue, String description, String configGroup, String configGroupDescription) {
        this.configKey = configKey;
        this.configValue = configValue;
        this.description = description;
        this.configGroup = configGroup;
        this.configGroupDescription = configGroupDescription;
    }

    public Config toEntity() {
        return Config.builder()
                .configKey(configKey)
                .configValue(configValue)
                .description(description)
                .configGroup(configGroup)
                .configGroupDescription(configGroupDescription)
                .build();

    }
}
