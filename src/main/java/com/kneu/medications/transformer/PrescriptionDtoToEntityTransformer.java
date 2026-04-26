package com.kneu.medications.transformer;

import com.kneu.medications.dto.PrescriptionDto;
import com.kneu.medications.jpa.entity.PrescriptionEntity;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionDtoToEntityTransformer implements Transformer<PrescriptionDto, PrescriptionEntity> {

    @Override
    public PrescriptionEntity transform(@NonNull PrescriptionDto dto) {
        return PrescriptionEntity.builder()
                .id(dto.getId())
                .label(dto.getLabel())
                .build();
    }
}


