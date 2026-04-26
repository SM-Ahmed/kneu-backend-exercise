package com.kneu.medications.service;

import com.kneu.medications.dto.MedicationDto;
import com.kneu.medications.persistence.MedicationPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationPersistenceService medicationPersistenceService;

    public List<MedicationDto> getAllMedications() {
        return medicationPersistenceService.getAllMedications();
    }
}
