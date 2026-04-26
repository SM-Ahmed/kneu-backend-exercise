package com.kneu.medications.transformer;

import com.kneu.medications.dto.PrescriptionMedicationDto;
import com.kneu.medications.jpa.entity.PrescriptionMedicationEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrescriptionMedicationEntityToDtoTransformer implements Transformer<PrescriptionMedicationEntity, PrescriptionMedicationDto> {

    @Override
    public PrescriptionMedicationDto transform(@NonNull PrescriptionMedicationEntity prescriptionMedicationEntity) {
        return PrescriptionMedicationDto.builder()
                        .medicationId(prescriptionMedicationEntity.getId().getMedicationId())
                        .administrationTime(prescriptionMedicationEntity.getAdministrationTime())
                        .build();
    }
}

