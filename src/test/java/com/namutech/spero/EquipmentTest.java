package com.namutech.spero;

import com.namutech.spero.common.config.RestTemplateConfig;
import com.namutech.spero.dto.EquipmentUpdateRequestDTO;
import com.namutech.spero.entity.Equipment;
import com.namutech.spero.service.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

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
            log.info("equipmentId: {}", Optional.ofNullable(equipment.getEquipmentId()));
            log.info("parentId: {}", Optional.ofNullable(equipment.getParent()).map(Equipment::getEquipmentId).orElse(null));
            String parentName = Optional.ofNullable(equipment.getParent()).map(Equipment::getName).orElse("None");
            log.info("장비이름: {}, 부모 장비 이름 : {}", equipment.getName(), parentName);
        }

        assertThat(equipmentList.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Equipment 상태 업데이트")
    public void updateEquipmentStatus() {
        Long equipmentId = 179L;
        EquipmentUpdateRequestDTO equipmentUpdateRequestDTO = EquipmentUpdateRequestDTO.builder()
                .status("ERROR")
                .build();
        EquipmentUpdateRequestDTO equipment = equipmentService.updateEquipmentStatus(equipmentId, equipmentUpdateRequestDTO);
        log.info("equipment: {}", equipment);

        assertThat(equipment.getStatus()).isEqualTo("ERROR");
    }


    @Test
    @DisplayName("Stream() 테스트")
    public void TestStreamAPI() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(num -> String.valueOf(num * num))
                .forEach(log::info);

        List<String> names = Arrays.asList("John", "Jane", "Jake", "Charles", "Tom");

        names.stream()
                .filter(name -> name.startsWith("J"))
                .sorted()
                .forEach(System.out::println);

        List<String> items = Arrays.asList("Apple", "Banana", "Apple", "Orange", "Banana", "Apple");
        Map<String, Long> itemCount = items.stream()
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()));
        log.info("itemCount:{}", itemCount);

        List<Integer> sum_numbers = Arrays.asList(1, 2, 3, 4, 5);
        int result = sum_numbers.stream()
                .reduce(0, Integer::sum);
        log.info("result:{}", result);

        Stream<String> words = Stream.of("Java", "Stream", "API");

        String words_result = words.map(String::toUpperCase)
                .reduce("", (a, b) -> a + " " + b)
                .trim();
        log.info("words_result: {}", words_result);
    }
}
