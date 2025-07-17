package com.namutech.spero.resource.adapter.cloud.aws;

import com.namutech.spero.dto.InstanceCreateRequestDTO;
import com.namutech.spero.resource.context.ResourceAttribute;
import com.namutech.spero.resource.context.ResourceContext;
import com.namutech.spero.dto.InstanceCreateResultResponseDTO;
import com.namutech.spero.entity.Cloud;
import com.namutech.spero.service.CloudService;
import com.namutech.spero.resource.port.ProvisionInstancePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("aws")
@RequiredArgsConstructor
public class AwsInstanceAdapter implements ProvisionInstancePort {

    // 포트를 구현한 어댑터 클래스입니다.
    // 이 클래스는 AWS 클라우드 서비스에 대한 인스턴스 프로비저닝을 담당합니다.
    private final CloudService cloudService;

    @Override
    public InstanceCreateResultResponseDTO createInstance(ResourceContext<InstanceCreateRequestDTO> context) {
        log.info("Creating AWS instance with request: {}", context);
        // AWS 인스턴스 생성 로직을 여기에 구현합니다.
        Cloud cloud = cloudService.getCloudById(context.getCloud().getCloudId());

        String instanceType = context.getRequestDto().getInstanceType();

        // vendor-specific 로직을 여기에 구현합니다.
        String region = context.getVendorAttr(ResourceAttribute.REGION, String.class);

        return InstanceCreateResultResponseDTO.builder()
                .instanceId("aws-instance-id")
                .cloudId(cloud.getCloudId())
                .region(region)
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
