package com.namutech.spero.enums;

import com.namutech.spero.entity.Config;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ConfigGroup {
    SYSTEM_PERFORMANCE("system-performance", "성능 데이터"),
    RETENTION("retention", "통계 데이터 보관 주기"),
    HEALTH_CHECK("health-check", "헬스 체크"),
    METERING("metering", "미터링 데이터"),
    INFLUXDB("influxDB", "influxDB 설정값"),
    STAT("stat", "라이프사이클 통계 데이터 보관 주기"),
    LOGEVENT("logEvent", "로그/이벤트 데이터 관리"),
    OSFLAVOR("osFlavor", "Flavor/Image 업데이트 주기"),
    COCKTAIL("cocktail", "cocktail 링크"),
    ONPREMISE("onPremise", "온프레미스 수집 주기"),
    INSTANCE_PROVISIONING("instanceProvisioning", "인스턴스 프로비저닝"),
    SERVICE_REQUEST("serviceRequest", "서비스 요청"),
    ETC("etc", "기타");

    private final String name;
    private final String description;

    ConfigGroup(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static ConfigGroup findByName(String name) {
        return Arrays.stream(ConfigGroup.values())
                .filter(group -> group.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 값이 없습니다."));
    }

    public static ConfigGroup fromName(String name) {
        for (ConfigGroup group : ConfigGroup.values()) {
            if (group.getName().equalsIgnoreCase(name)) {
                return group;
            }
        }
        throw new IllegalArgumentException("No ConfigGroup found for name: " + name);
    }
}
