package com.kneu.medications.jpa.entity;

import com.kneu.medications.jpa.key.PrescriptionMedicationPrimaryKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "prescription_medications")
public class PrescriptionMedicationEntity {

    @EmbeddedId
    private PrescriptionMedicationPrimaryKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("prescriptionId")
    @JoinColumn(name = "prescription_id", nullable = false)
    private PrescriptionEntity prescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("medicationId")
    @JoinColumn(name = "medication_id", nullable = false)
    private MedicationEntity medication;

    @Column
    private LocalTime administrationTime;
}

