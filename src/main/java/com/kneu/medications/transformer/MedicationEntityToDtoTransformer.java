package com.kneu.medications.transformer;

import com.kneu.medications.dto.MedicationDto;
import com.kneu.medications.jpa.entity.MedicationEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MedicationEntityToDtoTransformer implements Transformer<MedicationEntity, MedicationDto> {

    @Override
    public MedicationDto transform(MedicationEntity message) {
        return MedicationDto.builder()
                .id(Optional.ofNullable(message).map(MedicationEntity::getId).orElse(null))
                .name(Optional.ofNullable(message).map(MedicationEntity::getName).orElse(null))
                .dosage(Optional.ofNullable(message).map(MedicationEntity::getDosage).orElse(null))
                .build();
    }
}
