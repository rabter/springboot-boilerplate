package com.namutech.spero.service;

import com.namutech.spero.dto.WorkloadResourceNodeDTO;
import com.namutech.spero.dto.WorkloadResourcesResponseDTO;
import com.namutech.spero.entity.ServiceObservedK8sResource;
import com.namutech.spero.repository.ServiceObservedK8sResourceRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceObservedK8sResourceService {

    private static final Set<String> WORKLOAD_ROOT_KINDS =
            Set.of("DEPLOYMENT", "STATEFULSET", "DAEMONSET");

    private final ServiceObservedK8sResourceRepository serviceObservedK8sResourceRepository;

    @Transactional(readOnly = true)
    public WorkloadResourcesResponseDTO getWorkloadResourcesTree(Long serviceId, String cloudId, String namespace) {
        List<ServiceObservedK8sResource> entities = serviceObservedK8sResourceRepository.findByServiceIdAndCloudIdAndNamespace(serviceId, cloudId, namespace);

        WorkloadResourcesResponseDTO response = WorkloadResourcesResponseDTO.builder()
                .serviceId(serviceId)
                .cloudId(cloudId)
                .namespace(namespace)
                .workloads(new ArrayList<>())
                .orphans(new ArrayList<>())
                .build();

        if (entities == null || entities.isEmpty()) {
            return response;
        }

        // 1. Entity -> NodeDTO 맵 구성
        Map<Long, WorkloadResourceNodeDTO> nodeMap = entities.stream()
                .filter(e -> e.getObservedK8sResourceId() != null)
                .map(this::toNodeDTO)
                .collect(Collectors.toMap(WorkloadResourceNodeDTO::getId,
                        Function.identity(),
                        //중복 방어: 마지막 값을 사용
                        (a, b) -> b,
                        LinkedHashMap::new
                ));

        // 2. 부모-자식 연결
        List<WorkloadResourceNodeDTO> roots = new ArrayList<>();
        List<WorkloadResourceNodeDTO> orphans = new ArrayList<>();

        for (ServiceObservedK8sResource e : entities) {
            Long id = e.getObservedK8sResourceId();
            if (id == null) continue;

            WorkloadResourceNodeDTO node = nodeMap.get(id);
            Long parentId = e.getParentObservedK8sResourceId();

            if (parentId == null) {
                // parentId 자체가 없으면 root 후보
                roots.add(node);
                continue;
            }

            WorkloadResourceNodeDTO parent = nodeMap.get(parentId);
            if (parent == null) {
                // parentId는 있는데 부모 row가 조회 범위에 없음(관계 누락/수집 타이밍 이슈 등)
                orphans.add(node);
            } else {
                parent.getChildren().add(node);
            }
        }

        // 3. workload roots / non-workload roots(orphan-like) 분리
        List<WorkloadResourceNodeDTO> workloads = new ArrayList<>();
        for (WorkloadResourceNodeDTO root : roots) {
            if (root != null && WORKLOAD_ROOT_KINDS.contains(safeUpper(root.getKind()))) {
                workloads.add(root);
            } else {
                // workload 루트가 아닌데 parentId도 없는 경우 (ex. SERVICE/INGRESS 단독 관측)
                orphans.add(root);
            }
        }

       return response.toBuilder()
               .workloads(workloads)
               .orphans(orphans)
               .build();
    }


    /**
     * Observed YAML(detailYaml) 조회
     */
    @Transactional(readOnly = true)
    public String getObservedYaml(Long observedK8sResourceId) {
        ServiceObservedK8sResource e = serviceObservedK8sResourceRepository.findById(observedK8sResourceId)
                .orElseThrow(() -> new NoSuchElementException("Observed resource not found. id=" + observedK8sResourceId));

        return e.getDetailYaml();
    }

    /**
     * Observed 단건 조회(필요 시 DTO로 확장)
     */
    @Transactional
    public ServiceObservedK8sResource getObserved(Long observedK8sResourceId) {
        return serviceObservedK8sResourceRepository.findById(observedK8sResourceId)
                .orElseThrow(() -> new NoSuchElementException("Observed resouce not found. id=" + observedK8sResourceId));
    }


    private WorkloadResourceNodeDTO toNodeDTO(ServiceObservedK8sResource e) {
        return WorkloadResourceNodeDTO.builder()
                .id(e.getObservedK8sResourceId())
                .parentId(e.getParentObservedK8sResourceId())
                .kind(e.getKind())
                .name(e.getName())
                .status(e.getStatus())
                .resourceUid(e.getResourceUid())
                .children(new ArrayList<>())
                .build();

    }

    private String safeUpper(String v) {
        return v == null ? "" : v.toUpperCase(Locale.ROOT);
    }

}

