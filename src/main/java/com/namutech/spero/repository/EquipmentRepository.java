package com.namutech.spero.repository;

import com.namutech.spero.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    @Query("SELECT e FROM Equipment e LEFT JOIN FETCH e.parent")
    List<Equipment> findAllEquipmentWithParent();
}
