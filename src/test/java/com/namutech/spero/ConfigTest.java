package com.namutech.spero;

import com.namutech.spero.common.config.RestTemplateConfig;
import com.namutech.spero.dto.ConfigDTO;
import com.namutech.spero.dto.ConfigSearchConditionDTO;
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
    @DisplayName("Config 조회 테스트")
    public void getAllConfigsTest() {
        log.info("GetAllConfigs Test 입니다.");

        List<Config> configs = configService.getAllConfigs();

        // 검증
        assertNotNull(configs);
        assertEquals(20, configs.size());
        assertEquals("systemInterval", configs.get(0).getConfigKey(), "기대된 값과 다르게 나옴");

    }

    @Test
    @DisplayName("Config 생성 테스트")
    public void createConfigTest() {
        log.info("Create Config Test 입니다.");
        ConfigDTO configDTO = ConfigDTO.builder()
                .configKey("testKey1")
                .configValue("testValue1")
                .description("testDescription1")
                .configGroup(ConfigGroup.LOGEVENT.name())
                .configGroupDescription("testGroupDescription1")
                .build();

        Config config = configService.createConfig(configDTO);
        log.info("createAt: {}", config.getConfigValue());
        assertEquals(ConfigGroup.LOGEVENT.name(), config.getConfigGroup().name());
    }

//    @Test
    public void getDataExternalService() {
        ResponseEntity<String> res = configService.getData();

        log.info(res.getBody());
    }

//    @Test
    public void enumTest() {
        ConfigGroup group = ConfigGroup.fromName("systemPerformance");
        log.info("Enum Test 입니다.");
        log.info("group: {}", group);
    }

    @Test
    @DisplayName("동적 쿼리 테스트")
    public void getConfigWithConditionTest() {
        ConfigSearchConditionDTO condition = ConfigSearchConditionDTO.builder()
                .configKey("syste")
                .configValue(null)
                .build();
        List<Config> result = configService.getConfigWithConditions(condition);

        assertEquals(3, result.size());
    }
    @Test
    @DisplayName("QueryDSL BooleanExpression 테스트")
    public void dynamicQueryTest() {
        String configKeyParam = "doMetering";
        String configValueParam = "Y";

        List<Config> result = searchConfig(configKeyParam, configValueParam);

        assertFalse(result.isEmpty());
        assertEquals("doMetering", result.get(0).getConfigKey());
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
