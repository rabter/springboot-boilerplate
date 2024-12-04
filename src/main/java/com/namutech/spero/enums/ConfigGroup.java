package com.namutech.spero.enums;

import lombok.Getter;

@Getter
public enum ConfigGroup {
    LOGIN("login", "code001", "User login configuration"),
    DASHBOARD("dashboard", "code002", "Dashboard settings"),
    BILLING("billing", "code003", "Billing configurations"),
    SETTING("setting", "code004", "General settings"),
    DEFAULT("default", "code999", "Default Group");

    private final String name;
    private final String code;
    private final String description;

    ConfigGroup(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    // code로 ConfigGroup 찾기
    public static ConfigGroup findByCode(String code) {
        for (ConfigGroup group : values()) {
            if (group.getCode().equals(code)) {
                return group;
            }
        }
        return DEFAULT;
    }

    // name으로 ConfigGroup 찾기
    public static ConfigGroup findByName(String name) {
        for (ConfigGroup group : values()) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return DEFAULT;
    }

    // ConfigGroup이 유효한지 확인
    public boolean isValid() {
        return this != DEFAULT;
    }
}
