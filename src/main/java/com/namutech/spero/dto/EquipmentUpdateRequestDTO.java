package com.namutech.spero.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.namutech.spero.entity.Equipment;
import com.namutech.spero.enums.EquipmentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquipmentUpdateRequestDTO {

    private Long equipmentId;
    private String name;
    private Integer rackPosition;
    private String category;
    private String subCategory;
    private Integer rackUnit;
    private String ip;
    private String ipmi;
    private String status;
    private String faultDescription;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Builder
    public EquipmentUpdateRequestDTO(Long equipmentId, String name, Integer rackPosition, String category, String subCategory, Integer rackUnit, String ip, String ipmi, String status, String faultDescription, LocalDateTime createAt, LocalDateTime updateAt) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.rackPosition = rackPosition;
        this.category = category;
        this.subCategory = subCategory;
        this.rackUnit = rackUnit;
        this.ip = ip;
        this.ipmi = ipmi;
        this.status = status;
        this.faultDescription = faultDescription;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Equipment toEntity() {
        EquipmentStatus enumStatus;

        try {
            enumStatus = EquipmentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 status 값입니다: " + status);
        }
        return Equipment.builder()
                .equipmentId(equipmentId)
                .name(name)
                .rackPosition(rackPosition)
                .category(category)
                .subCategory(subCategory)
                .rackUnit(rackUnit)
                .ip(ip)
                .ipmi(ipmi)
                .status(enumStatus)
                .faultDescription(faultDescription)
                .createAt(createAt)
                .updateAt(updateAt)
                .build();
    }

    public static EquipmentUpdateRequestDTO of(Equipment equipment) {
        return EquipmentUpdateRequestDTO.builder()
                .equipmentId(equipment.getEquipmentId())
                .name(equipment.getName())
                .rackPosition(equipment.getRackPosition())
                .category(equipment.getCategory())
                .subCategory(equipment.getSubCategory())
                .rackUnit(equipment.getRackUnit())
                .ip(equipment.getIp())
                .ipmi(equipment.getIpmi())
                .status(equipment.getStatus().name())
                .faultDescription(equipment.getFaultDescription())
                .createAt(equipment.getCreateAt())
                .updateAt(equipment.getUpdateAt())
                .build();
    }

    public EquipmentStatus toEquipmentStatus() {
        return EquipmentStatus.valueOf(status.toUpperCase());
    }
}
