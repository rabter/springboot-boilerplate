package com.namutech.spero;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import com.namutech.spero.common.util.CommonUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@SpringBootTest
@Transactional
@Rollback
@Disabled // @Disabled 어노테이션은 테스트를 비활성화합니다. 필요에 따라 제거하세요.
public class CommonUtilTest {

    @Test
    public void convertByteToGigaByteTest() {
        Double gigaByte = CommonUtil.convertByteToGigaByte(13116561686528L);
        log.info("gigaByte: {}", gigaByte);
    }

    @Test
    public void convertByteToTeraByteTest() {
        Double teraByte = CommonUtil.convertByteToTeraByte(13116561686528L);
        log.info("teraByte: {}", teraByte);
    }

    @Test
    public void getVendorFromCloudIdTest() {
        String vendor = CommonUtil.getVendorFromCloudId("cloud007-aws-ap-northeast-3");
        log.info("vendor: {}", vendor);
    }

    @Test
    @DisplayName("getDurationFromTime 테스트")
    public void getDurationFromTimeTest() {
        String duration = CommonUtil.getDurationFromTime(LocalTime.of(10, 0), LocalTime.of(12, 0));
        log.info("duration: {}", duration);
    }

    @Test
    @DisplayName("getPeriodFromDate 테스트")
    public void getPeriodFromDateTest() {
        String period = CommonUtil.getPeriodFromDate(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31));
        log.info("period: {}", period);
    }

    @Test
    @DisplayName("getChronoUnitFromDateTime 테스트")
    public void getChronoUnitFromDateTimeTest() {
        String chronoUnit = CommonUtil.getChronoUnitFromDateTime(LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 12, 31, 23, 59));
        log.info("chronoUnit: {}", chronoUnit);
    }


}
