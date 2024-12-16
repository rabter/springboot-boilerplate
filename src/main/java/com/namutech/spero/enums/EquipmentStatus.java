package com.namutech.spero.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum EquipmentStatus {
    ACTIVE("active", 1),
    INACTIVE("inactive", 0),
    ERROR("error", 999);

    private final String value;
    private final int valueNum;

    EquipmentStatus(String value, int valueNum) {
        this.value = value;
        this.valueNum = valueNum;
    }

    public static EquipmentStatus ofValue(String value) {
        return Arrays.stream(EquipmentStatus.values())
                .filter(v -> v.getValue().equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 값입니다."));
    }

    public static EquipmentStatus ofValueNum(int valueNum) {
        return Arrays.stream(EquipmentStatus.values())
                .filter(v -> v.getValueNum() == valueNum)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 값입니다."));
    }
}
