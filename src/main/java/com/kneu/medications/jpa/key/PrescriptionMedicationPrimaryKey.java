package com.kneu.medications.jpa.key;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionMedicationPrimaryKey implements Serializable {

    @Column(name = "prescription_id")
    private Long prescriptionId;

    @Column(name = "medication_id")
    private Long medicationId;
}

