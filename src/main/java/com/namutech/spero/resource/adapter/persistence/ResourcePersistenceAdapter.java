package com.namutech.spero.resource.adapter.persistence;

import com.namutech.spero.resource.context.ResourceContext;
import com.namutech.spero.dto.InstanceCreateResultResponseDTO;
import com.namutech.spero.resource.port.ResourcePersistencePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResourcePersistenceAdapter implements ResourcePersistencePort {
    @Override
    public void saveInstanceWithResources(InstanceCreateResultResponseDTO result, ResourceContext context) {
        // 1. DB에 인스턴스 저장
        log.info("Saving instance with ID: {}", result.getInstanceId());

        // 2. DB에 인스턴스 볼륨 저장
        log.info("Saving volumes for instance ID: {}", result.getInstanceId());

        // 3. DB에 보안그룹 저장
        log.info("Saving security groups for instance ID: {}", result.getInstanceId());

        // 4. DB에 이력 저장
        log.info("Saving history for instance ID: {}", result.getInstanceId());
    }
}
