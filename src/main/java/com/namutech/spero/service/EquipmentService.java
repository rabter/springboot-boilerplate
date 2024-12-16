package com.namutech.spero.service;

import com.namutech.spero.entity.Equipment;
import com.namutech.spero.repository.EquipmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
