package com.namutech.spero.common.converter;

import com.namutech.spero.enums.EquipmentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class EquipmentStatusConverter implements AttributeConverter<EquipmentStatus,String> {

    @Override
    public String convertToDatabaseColumn(EquipmentStatus attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public EquipmentStatus convertToEntityAttribute(String dbData) {
        return EquipmentStatus.ofValue(dbData);
    }
}
