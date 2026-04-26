package com.kneu.medications.jpa.repository;

import com.kneu.medications.jpa.entity.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<MedicationEntity, Long> {
}
