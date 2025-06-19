package com.namutech.spero.service;

import com.namutech.spero.dto.ConfigDTO;
import com.namutech.spero.dto.ConfigSearchConditionDTO;
import com.namutech.spero.entity.Config;
import com.namutech.spero.enums.ConfigGroup;
import com.namutech.spero.repository.ConfigRepository;
import com.namutech.spero.repository.ConfigRepositoryCustom;
import com.namutech.spero.service.external.ExternalApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigService {

    private final JdbcTemplate jdbcTemplate;

    private final ConfigRepository configRepository;

    private final ConfigRepositoryCustom configRepositoryCustom;

    private final ExternalApiService externalApiService;

    public List<Config> getAllConfigs() {
        return configRepository.findAll();
    }

    public List<Config> getConfigWithConditions(ConfigSearchConditionDTO condition) {
        return configRepositoryCustom.findwithConditions(condition);
    }

    public ResponseEntity<String> getData() {
        String url = "https://www.naver.com";

        return externalApiService.getData(url);
    }

    @Transactional
    public Config createConfig(ConfigDTO configDTO) {
        try {
//  1. Builder 패턴 방식으로 생성 예제
//            Config config = configDTO.toEntity();
//  2. 정적 팩토리 메소드 방식으로 생성 예제
            Config config = Config.create(
                    configDTO.getConfigKey(),
                    configDTO.getConfigValue(),
                    configDTO.getDescription(),
                    ConfigGroup.valueOf(configDTO.getConfigGroup())
            );
            return configRepository.save(config);
        } catch (IllegalArgumentException e) {
            log.info("예외발생 : {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Bulk Insert 예제
     *
     */
    @Transactional
    public void createBulkConfig(List<ConfigDTO> configDTOList) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO tbConfig (configKey, configValue, description, configGroup) VALUES (?, ?, ?, ?)",
                configDTOList,
                configDTOList.size(),
                (ps, configDTO) -> {
                    ps.setString(1, configDTO.getConfigKey());
                    ps.setString(2, configDTO.getConfigValue());
                    ps.setString(3, configDTO.getDescription());
                    ps.setString(4, configDTO.getConfigGroup());
                });
    }
}
