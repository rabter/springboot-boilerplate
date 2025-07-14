package com.namutech.spero.service.adapter.persistence;

import com.namutech.spero.common.dto.ResourceContext;
import com.namutech.spero.dto.InstanceCreateRequestDTO;
import com.namutech.spero.entity.Cloud;
import com.namutech.spero.repository.CloudRepository;
import com.namutech.spero.service.port.ResourceQueryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

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
        ResourceContext context = ResourceContext.builder()
                .cloud(cloud)
                .requestDto(requestDto)
                .vendorSpecificData(new HashMap<>())
                .build();

        // vendor-specific data 설정
        context.putVendorAttr("region",
                Optional.ofNullable(requestDto.getVendorSpecificData())
                        .map(m ->m.get("region"))
                        .orElse(null));
        return context;
    }
}
