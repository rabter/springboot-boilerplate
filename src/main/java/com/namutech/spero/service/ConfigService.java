package com.namutech.spero.service;

import com.namutech.spero.dto.ConfigDTO;
import com.namutech.spero.entity.Config;
import com.namutech.spero.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConfigService {

    @Autowired
    private ConfigRepository configRepository;


    public List<Config> getAllConfigs() {
        return configRepository.findAll();
    }

    @Transactional
    public Config createConfig(ConfigDTO configDTO) {
        try {
            // Config config = configDTO.toEntity();
            Config config = Config.create(
                    configDTO.getConfigKey(),
                    configDTO.getConfigValue(),
                    configDTO.getDescription(),
                    configDTO.getConfigGroup()
            );
            return configRepository.save(config);
        } catch (IllegalArgumentException e) {
            System.out.println("예외발생 :" + e.getMessage());
            throw e;
        }
    }
}
