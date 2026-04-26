package com.kneu.medications.transformer;

import com.kneu.medications.dto.PrescriptionMedicationDto;
import com.kneu.medications.jpa.entity.PrescriptionMedicationEntity;
import com.kneu.medications.jpa.key.PrescriptionMedicationPrimaryKey;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrescriptionMedicationDtoToEntityTransformer implements Transformer<PrescriptionMedicationDto, PrescriptionMedicationEntity> {

    @Override
    public PrescriptionMedicationEntity transform(@NonNull PrescriptionMedicationDto dto) {
        return PrescriptionMedicationEntity.builder()
                .id(new PrescriptionMedicationPrimaryKey())
                .administrationTime(dto.getAdministrationTime())
                .build();
    }
}

