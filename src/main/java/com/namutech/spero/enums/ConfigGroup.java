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
}
