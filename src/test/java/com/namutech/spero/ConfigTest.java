package com.namutech.spero;

import com.namutech.spero.common.config.RestTemplateConfig;
import com.namutech.spero.common.util.CommonUtil;
import com.namutech.spero.dto.ConfigDTO;
import com.namutech.spero.entity.Config;
import com.namutech.spero.entity.QConfig;
import com.namutech.spero.enums.ConfigGroup;
import com.namutech.spero.repository.ConfigRepository;
import com.namutech.spero.service.ConfigService;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Import(RestTemplateConfig.class)
@Transactional
//@Commit
@Rollback
class ConfigTest {

    @Autowired
    private ConfigService configService;

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

//    @BeforeEach
//    public void setUp() {
//        //테스트용 데이터 추가
//        configRepository.save(Config.builder().configKey("configKey1").configValue("configValue1").configGroup(ConfigGroup.SETTING).configGroupDescription("groupDesc1").build());
//    }

    @Test
    public void contextLoads() {
        log.info("GetAllConfigs Test 입니다.");

        List<Config> configs = configService.getAllConfigs();

        // 검증
        assertNotNull(configs);
        assertEquals(2, configs.size());
        assertEquals("configKey2", configs.get(0).getConfigKey(), "기대된 값과 다르게 나옴");

    }

    @Test
    public void createConfigTest() {
        log.info("Create Config Test 입니다.");
        ConfigDTO configDTO = ConfigDTO.builder()
                .configKey("testKey1")
                .configValue("testValue1")
                .description("testDescription1")
                .configGroup(ConfigGroup.SETTING.name())
                .configGroupDescription("testGroupDescription1")
                .build();

        Config config = configService.createConfig(configDTO);
        log.info("createAt: {}", config.getConfigValue());
        assertEquals("SETTING", config.getConfigGroup().name());
    }

    @Test
    public void getDataExternalService() {
        ResponseEntity<String> res = configService.getData();

        log.info(res.getBody());
    }

    @Test
    public void enumTest() {
        ConfigGroup group = ConfigGroup.findByName("setting");
        log.info("Enum Test 입니다.");
        log.info("group: {}", group);
    }


    @Test
    @DisplayName("QueryDSL BooleanExpression 테스트입니다. ")
    public void dynamicQueryTest() {
        String configKeyParam = "testKey2";
        String configValueParam = "testValue2";

        List<Config> result = searchConfig(configKeyParam, configValueParam);

        assertEquals("testDescription1", result.get(0).getDescription());
    }

    private List<Config> searchConfig(String configKeyCond, String configValueCond) {
        return jpaQueryFactory
                .select(QConfig.config)
                .from(QConfig.config)
                .where(eqConfigKey(configKeyCond),
                        eqConfigValue(configValueCond))
                .fetch();
    }

    private BooleanExpression eqConfigKey(String configKeyCond) {
        if(Objects.isNull(configKeyCond)) { return null; }
        return QConfig.config.configKey.eq(configKeyCond);
    }

    private BooleanExpression eqConfigValue(String configValueCond) {
        if(Objects.isNull(configValueCond)) { return null; }
        return QConfig.config.configValue.eq(configValueCond);
    }
}
