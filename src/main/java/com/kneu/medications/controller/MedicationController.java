package com.kneu.medications.controller;

import java.util.List;

import com.kneu.medications.dto.MedicationDto;
import com.kneu.medications.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    @GetMapping("/medications")
    public List<MedicationDto> getAllMedications() {
        return medicationService.getAllMedications();
    }
}
