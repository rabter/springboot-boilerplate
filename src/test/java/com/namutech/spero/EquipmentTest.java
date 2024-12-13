package com.namutech.spero;

import com.namutech.spero.common.config.RestTemplateConfig;
import com.namutech.spero.entity.Equipment;
import com.namutech.spero.service.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Import(RestTemplateConfig.class)
@Transactional
//@Commit
@Rollback
public class EquipmentTest {

    @Autowired
    private EquipmentService equipmentService;

    @Test
    @DisplayName("Equipment 전체 조회")
    public void getAllEquipment() {
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        for (Equipment equipment : equipmentList) {
            String parentName = Optional.ofNullable(equipment.getParent()).map(Equipment::getName).orElse("None");
            log.info("장비이름: {}, 부모 장비 이름 : {}", equipment.getName(), parentName);
        }

        assertEquals(8, equipmentList.size());
    }
}
