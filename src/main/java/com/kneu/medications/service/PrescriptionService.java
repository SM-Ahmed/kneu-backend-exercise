package com.kneu.medications.service;

import com.kneu.medications.dto.PrescriptionDto;
import com.kneu.medications.persistence.PrescriptionPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionPersistenceService prescriptionPersistenceService;

    public List<PrescriptionDto> getAllPrescriptions() {
        return prescriptionPersistenceService.getAllPrescriptions();
    }

    public PrescriptionDto getPrescriptionById(Long prescriptionId) {
        if (Objects.isNull(prescriptionId)) {
            throw new IllegalArgumentException("Prescription ID cannot be null");
        }
        return prescriptionPersistenceService.getPrescriptionById(prescriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found with ID: " + prescriptionId));
    }

    public PrescriptionDto createPrescription(PrescriptionDto prescriptionDto) {
        if (Objects.isNull(prescriptionDto) || CollectionUtils.isEmpty(prescriptionDto.getPrescriptionMedications())) {
            throw new IllegalArgumentException("Invalid format in incoming request");
        }
        try {
            return prescriptionPersistenceService.createPrescriptionWithMedications(prescriptionDto);
        } catch (Exception exception) { // should specify specific exception; also should not catch any DB exception, should be caught in persistence with entityManager + flush and custom exception thrown for this service
            throw new IllegalArgumentException("Invalid medication reference", exception);
        }
    }
}
