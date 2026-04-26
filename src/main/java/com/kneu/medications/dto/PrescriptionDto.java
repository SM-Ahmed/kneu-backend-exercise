package com.kneu.medications.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDto {

    private Long id;
    private String label;
    private List<PrescriptionMedicationDto> prescriptionMedications;
}
