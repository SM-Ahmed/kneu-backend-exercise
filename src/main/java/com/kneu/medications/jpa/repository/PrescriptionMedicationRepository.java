package com.kneu.medications.jpa.repository;

import com.kneu.medications.jpa.entity.PrescriptionMedicationEntity;
import com.kneu.medications.jpa.key.PrescriptionMedicationPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionMedicationRepository extends JpaRepository<PrescriptionMedicationEntity, PrescriptionMedicationPrimaryKey> {
}

