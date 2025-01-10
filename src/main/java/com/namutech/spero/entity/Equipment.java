package com.namutech.spero.entity;

import com.namutech.spero.common.converter.EquipmentStatusConverter;
import com.namutech.spero.enums.EquipmentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tbOnpremEquipment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipmentId;
    private String name;
    private Integer rackPosition;
    private String category;
    private String subCategory;
    private Integer rackUnit;
    private String ip;
    private String ipmi;

    @Convert(converter = EquipmentStatusConverter.class)
//   @Enumerated(EnumType.STRING)
    private EquipmentStatus status;

    private String faultDescription;

    @ManyToOne
    @JoinColumn(name = "parentId", referencedColumnName = "equipmentId")
    private Equipment parent;

    private String oobmUsername;
    private String oobmPassword;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    @Builder
    public Equipment(Long equipmentId, String name, Integer rackPosition, String category, String subCategory, Integer rackUnit, String ip, String ipmi, EquipmentStatus status, String faultDescription, Equipment parent, String oobmUsername, String oobmPassword, LocalDateTime createAt, LocalDateTime updateAt) {
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
        this.parent = parent;
        this.oobmUsername = oobmUsername;
        this.oobmPassword = oobmPassword;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public void updateEquipmentStatus(EquipmentStatus status) {
        this.status = status;
    }
}
