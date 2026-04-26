package com.kneu.medications.controller;

import com.kneu.medications.dto.PrescriptionDto;
import com.kneu.medications.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PrescriptionController {

    //Should use AOP to have an advice class that catches standard API errors (e.g. missing argument)

    private final PrescriptionService prescriptionService;

    @GetMapping("/prescriptions")
    public List<PrescriptionDto> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }

    @GetMapping("/prescriptions/{id}")
    public ResponseEntity<PrescriptionDto> getPrescriptionById(@PathVariable Long id) {
        try {
            final PrescriptionDto responseBody = prescriptionService.getPrescriptionById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (IllegalArgumentException ex) { // Ideally: catch separate exception for when DB return zero rows and have proper error response for this case not 4xx as it is not user fault
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/prescriptions")
    public ResponseEntity<PrescriptionDto> createPrescription(@RequestBody PrescriptionDto prescriptionDto) {
        try { // Ideally: response created would be dedicated pojo defined in external schema, may contain other data like a generated request Id / timestamp.
            final PrescriptionDto responseBody = prescriptionService.createPrescription(prescriptionDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

