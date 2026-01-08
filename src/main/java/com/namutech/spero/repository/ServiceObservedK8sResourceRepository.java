package com.namutech.spero.repository;

import com.namutech.spero.entity.ServiceObservedK8sResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ServiceObservedK8sResourceRepository extends JpaRepository<ServiceObservedK8sResource, Long> {

    List<ServiceObservedK8sResource> findByServiceIdAndCloudIdAndNamespace(Long serviceId, String cloudId, String namespace);

    List<ServiceObservedK8sResource> findByServiceIdAndCloudIdAndNamespaceAndKindIn(Long serviceId, String cloudId, String namespace, Collection<String> kinds);

    List<ServiceObservedK8sResource> findByServiceIdAndCloudIdAndNamespaceAndKind(Long serviceId, String cloudId, String namespace, String kind);

    List<ServiceObservedK8sResource> findByParentObservedK8sResourceId(Long parentObservedK8sResourceId);

    Optional<ServiceObservedK8sResource> findByResourceUid(String resourceUid);
}
