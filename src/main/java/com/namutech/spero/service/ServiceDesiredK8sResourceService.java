package com.namutech.spero.service;

import com.namutech.spero.entity.ServiceDesiredK8sResource;
import com.namutech.spero.repository.ServiceDesiredK8sResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ServiceDesiredK8sResourceService {

    private final ServiceDesiredK8sResourceRepository serviceDesiredK8sResourceRepository;

    /**
     * Workload Resource 수정 화면에서 사용하는 최신 desiredYaml 조회
     */
    @Transactional(readOnly = true)
    public String getLatestDesiredYaml(Long desiredK8sResourceId) {
        ServiceDesiredK8sResource desiredK8sResource = serviceDesiredK8sResourceRepository
                .findById(desiredK8sResourceId).orElseThrow(() -> new NoSuchElementException("Disired resource not found. id="
                        + desiredK8sResourceId));

        return desiredK8sResource.getDesiredYaml();
    }

    /**
     * YAML 수정했을 때,
     * - 최신 desired 조회
     * - revision +1
     * - 신규 desired row 생성 (appliedYn = 'N')
     */
    @Transactional
    public ServiceDesiredK8sResource createServiceDesiredK8sResource(
            Long serviceId,
            String cloudId,
            String namespace,
            String kind,
            String name,
            String updatedDesiredYaml) {
        // 1. 최신 revision 조회
        int nextRevision = serviceDesiredK8sResourceRepository
                .findTopByServiceIdAndCloudIdAndNamespaceAndKindAndNameOrderByRevisionDesc(
                        serviceId, cloudId, namespace, kind, name
                )
                .map(ServiceDesiredK8sResource::getRevision)
                .map(r -> r + 1)
                .orElse(1);

        // 2. 신규 Desired 엔티티 생성
        ServiceDesiredK8sResource newEntity = ServiceDesiredK8sResource.builder()
                .serviceId(serviceId)
                .cloudId(cloudId)
                .namespace(namespace)
                .kind(kind)
                .name(name)
                .revision(nextRevision)
                .desiredYaml(updatedDesiredYaml)
                .appliedYn("N")
                .build();

        // 3. DB 저장(INSERT)
        return serviceDesiredK8sResourceRepository.save(newEntity);
    }
}
