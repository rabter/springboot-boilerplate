package com.namutech.spero;

import com.namutech.spero.commonlib.common.util.DateTimeUtil;
import com.namutech.spero.commonlib.entity.Workspace;
import com.namutech.spero.dto.InstanceCreateRequestDTO;
import com.namutech.spero.resource.context.ResourceAttribute;
import com.namutech.spero.service.ResourceManagerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@SpringBootTest
@Transactional
@Rollback
public class HexagonalArchitechtureTest {

    @Autowired
    private ResourceManagerService resourceManagerService;

    @Test
    @DisplayName("AWS Instance 생성 Test")
    public void createAWSInstanceTest() {
        // 테스트 로직을 작성합니다.
        // 예를 들어, 인스턴스 생성 요청을 보내고 결과를 검증하는 등의 작업을 수행할 수 있습니다.

        log.info("Hexagonal Architecture Test 실행");
        log.info("AWS 인스턴스 생성 예제");
        InstanceCreateRequestDTO requestDto = InstanceCreateRequestDTO.builder()
                .cloudId("cloud005-aws-ap-northeast-2")
                .vendor("aws")
                .instanceType("t2.micro")
                .imageId("ami-12345678")
                .securityGroupId("sg-12345678")
                .keyPairName("my-key-pair")
                .vendorSpecificData(Map.of(ResourceAttribute.REGION, "ap-northeast-2")) // AWS의 경우 지역 정보 추가
                .build();
         resourceManagerService.createInstance(requestDto);


        // Assertions 등을 사용하여 결과를 검증합니다.
    }

    @Test
    @DisplayName("Azure Instance 생성 Test")
    public void createAzureInstanceTest() {
        // 테스트 로직을 작성합니다.
        // 예를 들어, 인스턴스 생성 요청을 보내고 결과를 검증하는 등의 작업을 수행할 수 있습니다.

        log.info("Azure 인스턴스 생성 예제");
        InstanceCreateRequestDTO requestDto = InstanceCreateRequestDTO.builder()
                .cloudId("cloud2029-azure-test")
                .vendor("azure")
                .instanceType("Standard_B1s")
                .imageId("Canonical:UbuntuServer:18.04-LTS:latest")
                .securityGroupId("my-security-group")
                .keyPairName("my-azure-key-pair")
                .build();
         resourceManagerService.createInstance(requestDto);

        // Assertions 등을 사용하여 결과를 검증합니다.
    }

    @Test
    @DisplayName("Commonlib 의존성 테스트")
    public void commonlibDependencyTest() {
        // Commonlib 의존성이 제대로 작동하는지 확인하는 테스트를 작성합니다.
        // 예를 들어, DateTimeUtil 클래스의 메서드를 호출하고 결과를 검증할 수 있습니다.

        log.info("Commonlib 의존성 테스트 실행");
        log.info(DateTimeUtil.nowToString());
        Workspace workspace = Workspace.builder()
                .name("Test Workspace")
                .build();
    }
}
