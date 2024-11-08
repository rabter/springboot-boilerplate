package com.namutech.spero.service;

import com.namutech.spero.dto.ConfigDTO;
import com.namutech.spero.entity.Config;
import com.namutech.spero.enums.ConfigGroup;
import com.namutech.spero.repository.ConfigRepository;
import com.namutech.spero.service.external.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private ExternalApiService externalApiService;

    public List<Config> getAllConfigs() {
        return configRepository.findAll();
    }

    public ResponseEntity<String> getData() {
        String url = "https://www.naver.com";

        return externalApiService.getData(url);
    }

    @Transactional
    public Config createConfig(ConfigDTO configDTO) {
        try {
            Config config = configDTO.toEntity();

//            # 정적 팩토리 메소드 방식으로 생성 예제
//            Config config = Config.create(
//                    configDTO.getConfigKey(),
//                    configDTO.getConfigValue(),
//                    configDTO.getDescription(),
//                    ConfigGroup.valueOf(configDTO.getConfigGroup())
//            );
            return configRepository.save(config);
        } catch (IllegalArgumentException e) {
            System.out.println("예외발생 :" + e.getMessage());
            throw e;
        }
    }
}
