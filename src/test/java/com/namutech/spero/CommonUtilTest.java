package com.namutech.spero;

import com.namutech.spero.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
@Rollback
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
}
