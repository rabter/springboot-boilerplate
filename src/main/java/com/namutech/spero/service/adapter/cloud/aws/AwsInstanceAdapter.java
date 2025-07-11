package com.namutech.spero.service.adapter.cloud.aws;

import com.namutech.spero.common.dto.ResourceContext;
import com.namutech.spero.dto.InstanceCreateResultResponseDTO;
import com.namutech.spero.enums.EquipmentStatus;
import com.namutech.spero.enums.VendorType;
import com.namutech.spero.service.port.ProvisionInstancePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service("aws")
public class AwsInstanceAdapter implements ProvisionInstancePort {

    // 포트를 구현한 어댑터 클래스입니다.
    // 이 클래스는 AWS 클라우드 서비스에 대한 인스턴스 프로비저닝을 담당합니다.

    @Override
    public InstanceCreateResultResponseDTO createInstance(ResourceContext context) {
        log.info("Creating AWS instance with request: {}", context);
        // AWS 인스턴스 생성 로직을 여기에 구현합니다.
        return InstanceCreateResultResponseDTO.builder()
                .instanceId("aws-instance-id")
                .build();
    }

    @Override
    public void startInstance(String instanceId) {

    }

    @Override
    public void stopInstance(String instanceId) {

    }

    @Override
    public void deleteInstance(String instanceId) {

    }

    @Override
    public String getInstanceStatus(String instanceId) {
        return "";
    }
}
