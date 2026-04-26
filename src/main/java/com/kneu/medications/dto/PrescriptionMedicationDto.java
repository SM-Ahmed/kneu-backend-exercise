package com.kneu.medications.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionMedicationDto {

    private Long prescriptionId;
    private Long medicationId;
    private LocalTime administrationTime;
}
