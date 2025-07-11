package com.namutech.spero.service;

import com.namutech.spero.common.dto.ResourceContext;
import com.namutech.spero.dto.InstanceCreateRequestDTO;
import com.namutech.spero.dto.InstanceCreateResultResponseDTO;
import com.namutech.spero.service.port.ProvisionInstancePort;
import com.namutech.spero.service.port.ResourcePersistencePort;
import com.namutech.spero.service.port.ResourceQueryPort;
import com.namutech.spero.service.router.ProvisionPortRouter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceManagerService {
    // 고수준 모듈(ResourceManagerService)에서 저수준 모듈(VMWareInstanceService, AzureInstanceService, OvirtInstanceService)을 사용합니다.
    // 고수준 모듈은 저수준 모듈에 의존하지 않고, 인터페이스를 통해 의존성을 역전시킵니다.(Dependency Inversion Principle)

    private final ResourceQueryPort resourceQueryPort;
    private final ProvisionPortRouter provisionPortRouter;
    private final ResourcePersistencePort resourcePersistencePort;

    @Transactional
    public void createInstance(String cloudId, InstanceCreateRequestDTO requestDto) {
        // DB 조회 및 context 생성(queryPort로 캡슐화)
        ResourceContext context = resourceQueryPort.buildProvisioningContext(cloudId, requestDto);
        log.info("DB 조회");

        // CSP 인스턴스 생성
        ProvisionInstancePort instancePort = provisionPortRouter.getProvisionInstancePort(context.getCloud().getVendor());
        InstanceCreateResultResponseDTO result = instancePort.createInstance(context);
        log.info("Instance 생성 완료: {}", result.getInstanceId());

        // DB 저장
        resourcePersistencePort.saveInstanceWithResources(result, context);
        log.info("Instance와 리소스 정보 저장 완료: {}", result.getInstanceId());
    }
}
