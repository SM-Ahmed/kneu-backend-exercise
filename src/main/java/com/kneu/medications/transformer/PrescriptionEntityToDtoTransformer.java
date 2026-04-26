package com.kneu.medications.transformer;

import com.kneu.medications.dto.PrescriptionDto;
import com.kneu.medications.dto.PrescriptionMedicationDto;
import com.kneu.medications.jpa.entity.PrescriptionEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PrescriptionEntityToDtoTransformer implements Transformer<PrescriptionEntity, PrescriptionDto> {

    private final PrescriptionMedicationEntityToDtoTransformer prescriptionMedicationTransformer;

    @Override
    public PrescriptionDto transform(@NonNull PrescriptionEntity prescriptionEntity) {
        return PrescriptionDto.builder()
                        .id(prescriptionEntity.getId())
                        .label(prescriptionEntity.getLabel())
                        .prescriptionMedications(mapPrescriptionMedications(prescriptionEntity))
                        .build();
    }

    private List<PrescriptionMedicationDto> mapPrescriptionMedications(@NonNull PrescriptionEntity prescriptionEntity) {
        return Optional.ofNullable(prescriptionEntity.getPrescriptionMedications())
                .map(medicationEntities -> medicationEntities.stream()
                        .map(prescriptionMedicationTransformer::transform)
                        .toList())
                .orElse(List.of());
    }
}

