package com.kneu.medications.persistence;

import com.kneu.medications.dto.PrescriptionDto;
import com.kneu.medications.dto.PrescriptionMedicationDto;
import com.kneu.medications.jpa.entity.MedicationEntity;
import com.kneu.medications.jpa.entity.PrescriptionEntity;
import com.kneu.medications.jpa.entity.PrescriptionMedicationEntity;
import com.kneu.medications.jpa.repository.MedicationRepository;
import com.kneu.medications.jpa.repository.PrescriptionMedicationRepository;
import com.kneu.medications.jpa.repository.PrescriptionRepository;
import com.kneu.medications.transformer.PrescriptionDtoToEntityTransformer;
import com.kneu.medications.transformer.PrescriptionEntityToDtoTransformer;
import com.kneu.medications.transformer.PrescriptionMedicationDtoToEntityTransformer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrescriptionPersistenceService {

    private final MedicationRepository medicationRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMedicationRepository prescriptionMedicationRepository;
    private final PrescriptionDtoToEntityTransformer prescriptionDtoToEntityTransformer;
    private final PrescriptionEntityToDtoTransformer prescriptionEntityToDtoTransformer;
    private final PrescriptionMedicationDtoToEntityTransformer prescriptionMedicationDtoToEntityTransformer;



    public List<PrescriptionDto> getAllPrescriptions() {
        final List<PrescriptionEntity> prescriptionEntities = prescriptionRepository.findAll();
        return prescriptionEntities.stream().map(prescriptionEntityToDtoTransformer::transform).toList();
    }

    public Optional<PrescriptionDto> getPrescriptionById(Long prescriptionId) {
        final Optional<PrescriptionEntity> prescriptionEntityOptional = prescriptionRepository.findById(prescriptionId);
        return prescriptionEntityOptional.map(prescriptionEntityToDtoTransformer::transform);
    }

    @Transactional
    public PrescriptionDto createPrescriptionWithMedications(@NonNull PrescriptionDto prescriptionDto) {
        final PrescriptionEntity prescriptionEntity = prescriptionDtoToEntityTransformer.transform(prescriptionDto);
        final PrescriptionEntity savedPrescriptionEntity = prescriptionRepository.save(prescriptionEntity);

        final List<PrescriptionMedicationEntity> prescriptionMedicationEntityList = prescriptionDto.getPrescriptionMedications()
                .stream()
                .map(prescriptionMedicationDto -> buildPrescriptionMedicationEntity(savedPrescriptionEntity, prescriptionMedicationDto))
                .toList();
        final List<PrescriptionMedicationEntity> savedPrescriptionMedicationEntityList = prescriptionMedicationRepository.saveAll(prescriptionMedicationEntityList);

        savedPrescriptionEntity.setPrescriptionMedications(savedPrescriptionMedicationEntityList);
        return prescriptionEntityToDtoTransformer.transform(savedPrescriptionEntity);
    }

    private PrescriptionMedicationEntity buildPrescriptionMedicationEntity(PrescriptionEntity savedPrescriptionEntity, PrescriptionMedicationDto prescriptionMedicationDto) {
        final MedicationEntity existingMedicationEntity = medicationRepository.getReferenceById(prescriptionMedicationDto.getMedicationId());
        final PrescriptionMedicationEntity prescriptionMedicationEntity = prescriptionMedicationDtoToEntityTransformer.transform(prescriptionMedicationDto);
        prescriptionMedicationEntity.setPrescription(savedPrescriptionEntity);
        prescriptionMedicationEntity.setMedication(existingMedicationEntity);
        return prescriptionMedicationEntity;
    }

}
