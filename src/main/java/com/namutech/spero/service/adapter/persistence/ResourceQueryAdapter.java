package com.namutech.spero.service.adapter.persistence;

import com.namutech.spero.common.dto.ResourceContext;
import com.namutech.spero.dto.InstanceCreateRequestDTO;
import com.namutech.spero.entity.Cloud;
import com.namutech.spero.repository.CloudRepository;
import com.namutech.spero.service.port.ResourceQueryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResourceQueryAdapter implements ResourceQueryPort {

    private final CloudRepository cloudRepository;

    @Override
    public ResourceContext buildProvisioningContext(String cloudId, InstanceCreateRequestDTO requestDto) {
        Cloud cloud = cloudRepository.findById(cloudId)
                .orElseThrow(() -> new IllegalArgumentException("Cloud not found with id: " + cloudId));
        log.info("CloudName: {}", cloud.getCloudName());
        return ResourceContext.builder()
                .cloud(cloud)
                .build();
    }
}
