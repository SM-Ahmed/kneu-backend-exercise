package com.kneu.medications.persistence;

import com.kneu.medications.dto.MedicationDto;
import com.kneu.medications.jpa.entity.MedicationEntity;
import com.kneu.medications.jpa.repository.MedicationRepository;
import com.kneu.medications.transformer.MedicationEntityToDtoTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationPersistenceService {

    private final MedicationRepository medicationRepository;
    private final MedicationEntityToDtoTransformer medicationEntityToDtoTransformer;

    public List<MedicationDto> getAllMedications() {
        final List<MedicationEntity> medicationEntities = medicationRepository.findAll();
        return medicationEntities.stream().map(medicationEntityToDtoTransformer::transform).toList();
    }
}
