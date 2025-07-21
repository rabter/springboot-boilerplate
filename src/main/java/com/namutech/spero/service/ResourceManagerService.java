package com.namutech.spero.service;

import com.namutech.spero.resource.context.ResourceContext;
import com.namutech.spero.dto.InstanceCreateRequestDTO;
import com.namutech.spero.dto.InstanceCreateResultResponseDTO;
import com.namutech.spero.resource.port.ProvisionInstancePort;
import com.namutech.spero.resource.port.PersistenceInstancePort;
import com.namutech.spero.resource.port.QueryInstancePort;
import com.namutech.spero.resource.router.ProvisionPortRouter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceManagerService {
    /**
     * USECASE : 인스턴스 생성
     */

    private final QueryInstancePort queryInstancePort;
    private final ProvisionPortRouter provisionPortRouter;
    private final PersistenceInstancePort persistenceInstancePort;

    @Transactional
    public void createInstance(InstanceCreateRequestDTO requestDto) {
        // DB 조회 및 context 생성(queryPort로 캡슐화)
        ResourceContext<InstanceCreateRequestDTO> context = queryInstancePort.buildInstanceProvisioningContext(requestDto);
        log.info("DB 조회");

        // CSP 인스턴스 생성
        ProvisionInstancePort instancePort = provisionPortRouter.getProvisionInstancePort(context.getCloud().getVendor());
        InstanceCreateResultResponseDTO result = instancePort.createInstance(context);
        log.info("Instance 생성 완료: {}", result.getInstanceId());

        // DB 저장
        persistenceInstancePort.saveInstanceWithResources(result, context);
        log.info("Instance와 리소스 정보 저장 완료: {}", result.getInstanceId());
    }

//    @Transactional
//    public VpcCreateResponseDTO createVpc(String cloudId, VpcCreateRequestDTO dto) {
//        // DB 조회 및 context 생성(queryPort로 캡슐화)
//        ResourceContext context = resourceQueryPort.buildInstanceProvisioningContext(cloudId, dto);
//        log.info("DB 조회");
//
//        // CSP VPC 생성
//        ProvisionVpcPort vpcPort = provisionPortRouter.getProvisionVpcPort(context.getCloud().getVendor());
//        VpcCreateResponseDTO result = vpcPort.createVpc(context);
//        log.info("VPC 생성 완료: {}", result.getVpcId());
//
//        // DB 저장
//        resourcePersistencePort.saveVpcWithResources(result, context);
//        log.info("VPC와 리소스 정보 저장 완료: {}", result.getVpcId());
//
//        return result;
//    }
}
