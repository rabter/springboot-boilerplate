package com.namutech.spero;

import com.namutech.spero.common.config.RestTemplateConfig;
import com.namutech.spero.dto.ConfigDTO;
import com.namutech.spero.entity.Config;
import com.namutech.spero.enums.ConfigGroup;
import com.namutech.spero.repository.ConfigRepository;
import com.namutech.spero.service.ConfigService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(RestTemplateConfig.class)
@Transactional
//@Commit
@Rollback
class SperoApplicationTests {

    @Autowired
    private ConfigService configService;

    @Autowired
    private ConfigRepository configRepository;

//    @BeforeEach
//    public void setUp() {
//        //테스트용 데이터 추가
//        configRepository.save(Config.builder().configKey("configKey1").configValue("configValue1").configGroup(ConfigGroup.SETTING).configGroupDescription("groupDesc1").build());
//    }

    @Test
    public void contextLoads() {
        System.out.println("GetAllConfigs Test 입니다.");

        List<Config> configs = configService.getAllConfigs();

        // 검증
        assertNotNull(configs);
        assertEquals(2, configs.size());
        assertEquals("configKey2", configs.get(0).getConfigKey(), "기대된 값과 다르게 나옴");

    }

    @Test
    public void createConfigTest() {
        System.out.println("Create Config Test 입니다.");
        ConfigDTO configDTO = ConfigDTO.builder()
                .configKey("testKey1")
                .configValue("testValue1")
                .description("testDescription1")
                .configGroup(ConfigGroup.SETTING.name())
                .configGroupDescription("testGroupDescription1")
                .build();

        Config config = configService.createConfig(configDTO);
        System.out.println("createAt: " + config.getConfigValue());
        assertEquals("SETTING", config.getConfigGroup().name());
    }

    @Test
    public void getDataExternalService() {
        ResponseEntity<String> res = configService.getData();

        System.out.println(res.getBody());
    }

}
