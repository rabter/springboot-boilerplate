package com.namutech.spero.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkloadResourceNodeDTO {

    private Long id;
    private Long parentId;
    private String kind;
    private String name;

    private String status;
    private String resourceUid;

    private List<WorkloadResourceNodeDTO> children;

    @Builder
    public WorkloadResourceNodeDTO(Long id, Long parentId, String kind, String name, String status, String resourceUid, List<WorkloadResourceNodeDTO> children) {
        this.id = id;
        this.parentId = parentId;
        this.kind = kind;
        this.name = name;
        this.status = status;
        this.resourceUid = resourceUid;
        this.children = children;
    }

}
