package com.namutech.spero.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tbConfig")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long configId;

    private String configKey;
    private String configValue;
    private String description;

    private String configGroup;

    private String configGroupDescription;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    @Builder
    public Config(String configKey, String configValue, String description, String configGroup, String configGroupDescription) {
        this.configKey = configKey;
        this.configValue = configValue;
        this.description = description;
        this.configGroup = configGroup;
        this.configGroupDescription = configGroupDescription;
    }

    /**
     *  정적 팩토리 메서드
     * @param configKey (설정 키)
     * @param configValue (설정 값)
     * @param description (설명)
     * @return Config
     */
    public static Config create(String configKey, String configValue, String description, String configGroup) {

        // 필수 유효성 검사
        if (configKey == null || configKey.isEmpty()) {
            throw new IllegalArgumentException("configKey는 반드시 존재해야 합니다.");
        }

        // 커스터마이징
        if (configGroup == null || configGroup.isEmpty()) {
            configGroup = "DEFAULT_GROUP";
        }

        return Config.builder()
                .configKey(configKey)
                .configValue(configValue)
                .description(description)
                .configGroup(configGroup)
                .build();
    }

}
