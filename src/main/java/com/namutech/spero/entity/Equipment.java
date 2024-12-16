package com.namutech.spero.entity;

import com.namutech.spero.common.converter.EquipmentStatusConverter;
import com.namutech.spero.enums.EquipmentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tbOnpremEquipment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equipmentId;
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
}
