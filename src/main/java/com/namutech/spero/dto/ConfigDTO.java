package com.namutech.spero.dto;

import com.namutech.spero.common.exception.InvalidConfigGroupException;
import com.namutech.spero.entity.Config;
import com.namutech.spero.enums.ConfigGroup;
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
        ConfigGroup enumConfigGroup;

        try {
            enumConfigGroup = ConfigGroup.valueOf(configGroup.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidConfigGroupException("유효하지 않은 configGroup 값입니다: " + configGroup);
        }

        return Config.builder()
                .configKey(configKey)
                .configValue(configValue)
                .description(description)
                .configGroup(enumConfigGroup)
                .configGroupDescription(configGroupDescription)
                .build();
    }
}
