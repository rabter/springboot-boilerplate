package com.namutech.spero.resource.adapter.persistence;

import com.namutech.spero.resource.context.ResourceAttribute;
import com.namutech.spero.resource.context.ResourceContext;
import com.namutech.spero.dto.InstanceCreateRequestDTO;
import com.namutech.spero.entity.Cloud;
import com.namutech.spero.repository.CloudRepository;
import com.namutech.spero.resource.port.QueryInstancePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueryInstanceAdapter implements QueryInstancePort {

    private final CloudRepository cloudRepository;

    @Override
    public ResourceContext<InstanceCreateRequestDTO> buildInstanceProvisioningContext(InstanceCreateRequestDTO requestDto) {
        String cloudId = requestDto.getCloudId();
        Cloud cloud = cloudRepository.findById(cloudId)
                .orElseThrow(() -> new IllegalArgumentException("Cloud not found with id: " + cloudId));
        log.info("CloudName: {}", cloud.getCloudName());
        ResourceContext<InstanceCreateRequestDTO> context = ResourceContext.<InstanceCreateRequestDTO>builder()
                .cloud(cloud)
                .requestDto(requestDto)
                .build();

        // vendor-specific data 설정
        context.putVendorAttr(ResourceAttribute.REGION,
                Optional.ofNullable(requestDto.getVendorSpecificData())
                        .map(m -> m.get(ResourceAttribute.REGION))
                        .orElse(null));
        return context;
    }
}
