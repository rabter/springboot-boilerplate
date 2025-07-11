package com.namutech.spero.service.adapter.cloud.azure;

import com.namutech.spero.common.dto.ResourceContext;
import com.namutech.spero.dto.InstanceCreateResultResponseDTO;
import com.namutech.spero.service.port.ProvisionInstancePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("azure")
public class AzureInstanceAdapter implements ProvisionInstancePort {
    @Override
    public InstanceCreateResultResponseDTO createInstance(ResourceContext context) {
        log.info("Creating Azure instance with request: {}", context);
        // Azure 인스턴스 생성 로직을 여기에 구현합니다.
        return InstanceCreateResultResponseDTO.builder()
                .instanceId("azure-instance-id")
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
