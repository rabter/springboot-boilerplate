package com.namutech.spero.service;

import com.namutech.spero.dto.EquipmentUpdateRequestDTO;
import com.namutech.spero.entity.Equipment;
import com.namutech.spero.repository.EquipmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }
    public List<Equipment> getAllEquipmentWithParent() {
        return equipmentRepository.findAllEquipmentWithParent();
    }

    @Transactional
    public EquipmentUpdateRequestDTO updateEquipmentStatus(Long equipmentId, EquipmentUpdateRequestDTO equipmentDTO) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장비가 없습니다. id=" + equipmentId));

        equipment.updateEquipmentStatus(equipmentDTO.toEquipmentStatus());

        return EquipmentUpdateRequestDTO.of(equipment);
    }
}
