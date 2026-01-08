package com.namutech.spero.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkloadResourcesResponseDTO {

    private Long serviceId;
    private String cloudId;
    private String namespace;

    /**
     * Workload root 노드
     * - kind : DEPLOYMENT | STATEFULSET | DAEMONSET
     */
    private List<WorkloadResourceNodeDTO> workloads;

    /**
     *  부모 리소스를 찾지 못했거나
     *  Workload root가 아닌 루트 리소스
     */
    private List<WorkloadResourceNodeDTO> orphans;

    @Builder(toBuilder = true)
    public WorkloadResourcesResponseDTO(Long serviceId, String cloudId, String namespace, List<WorkloadResourceNodeDTO> workloads, List<WorkloadResourceNodeDTO> orphans) {
        this.serviceId = serviceId;
        this.cloudId = cloudId;
        this.namespace = namespace;
        this.workloads = workloads;
        this.orphans = orphans;
    }
}
