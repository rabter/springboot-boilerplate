package com.namutech.spero.repository;

import com.namutech.spero.entity.ServiceDesiredK8sResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceDesiredK8sResourceRepository extends JpaRepository<ServiceDesiredK8sResource, Long> {

    List<ServiceDesiredK8sResource> findByServiceIdAndCloudIdAndNamespace(Long serviceId, String cloudId, String namespace);

    Optional<ServiceDesiredK8sResource> findTopByServiceIdAndCloudIdAndNamespaceAndKindAndNameOrderByRevisionDesc(Long serviceId, String cloudId, String namespace, String kind, String name);

    List<ServiceDesiredK8sResource> findByServiceIdAndCloudIdAndNamespaceAndKindAndNameOrderByRevisionDesc(Long ServiceId, String cloudId, String namespace, String kind, String name);
}
