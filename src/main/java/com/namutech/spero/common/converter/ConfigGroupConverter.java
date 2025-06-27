package com.namutech.spero.common.converter;

import com.namutech.spero.enums.ConfigGroup;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ConfigGroupConverter implements AttributeConverter<ConfigGroup, String> {

    @Override
    public String convertToDatabaseColumn(ConfigGroup attribute) {
        return attribute != null ? attribute.getName() : null;
    }

    @Override
    public ConfigGroup convertToEntityAttribute(String dbData) {
        return ConfigGroup.fromName(dbData);
    }
}
