package com.kneu.medications.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kneu.medications.dto.PrescriptionDto;
import com.kneu.medications.dto.PrescriptionMedicationDto;
import com.kneu.medications.jpa.entity.MedicationEntity;
import com.kneu.medications.jpa.repository.MedicationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PrescriptionEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test //Note: should be split in separate smaller tests
    void prescriptionEndToEndTest() throws Exception {
        // Setup: Insert some medications
        medicationRepository.save(MedicationEntity.builder().name("Aspirin").dosage("100mg").build());
        medicationRepository.save(MedicationEntity.builder().name("Metformin").dosage("500mg").build());
        medicationRepository.save(MedicationEntity.builder().name("Lisinopril").dosage("10mg").build());

        // Step 1: Get all prescriptions - should be empty
        mockMvc.perform(get("/prescriptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        // Step 2: Create a prescription with 2 valid medicines
        PrescriptionDto createRequest = PrescriptionDto.builder()
                .label("Morning Medications")
                .prescriptionMedications(Arrays.asList(
                        PrescriptionMedicationDto.builder()
                                .medicationId(1L)
                                .administrationTime(LocalTime.of(8, 0))
                                .build(),
                        PrescriptionMedicationDto.builder()
                                .medicationId(2L)
                                .administrationTime(LocalTime.of(8, 30))
                                .build()
                ))
                .build();

        mockMvc.perform(post("/prescriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.label", equalTo("Morning Medications")))
                .andExpect(jsonPath("$.prescriptionMedications", hasSize(2)));

        // Step 3: Get all prescriptions - should have 1 entry with correct data
        mockMvc.perform(get("/prescriptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].label", equalTo("Morning Medications")))
                .andExpect(jsonPath("$[0].prescriptionMedications", hasSize(2)))
                .andExpect(jsonPath("$[0].prescriptionMedications[0].medicationId", equalTo(1)))
                .andExpect(jsonPath("$[0].prescriptionMedications[0].administrationTime", equalTo("08:00:00")))
                .andExpect(jsonPath("$[0].prescriptionMedications[1].medicationId", equalTo(2)))
                .andExpect(jsonPath("$[0].prescriptionMedications[1].administrationTime", equalTo("08:30:00")));

        // Step 4: Get prescription by ID (1) - should return the prescription
        mockMvc.perform(get("/prescriptions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.label", equalTo("Morning Medications")))
                .andExpect(jsonPath("$.prescriptionMedications", hasSize(2)))
                .andExpect(jsonPath("$.prescriptionMedications[0].medicationId", equalTo(1)))
                .andExpect(jsonPath("$.prescriptionMedications[1].medicationId", equalTo(2)));

        // Step 5: Get prescription by invalid ID (999) - should return 404/error
        mockMvc.perform(get("/prescriptions/999"))
                .andExpect(status().isBadRequest());

        // Step 6: Try to create prescription with invalid medication ID (10) - should fail
        PrescriptionDto invalidRequest = PrescriptionDto.builder()
                .label("Invalid Prescription")
                .prescriptionMedications(Collections.singletonList(
                        PrescriptionMedicationDto.builder()
                                .medicationId(10L)  // Invalid ID - does not exist
                                .administrationTime(LocalTime.of(9, 0))
                                .build()
                ))
                .build();

        mockMvc.perform(post("/prescriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        // Step 7: Get all prescriptions again - should still only have 1 entry
        mockMvc.perform(get("/prescriptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].label", equalTo("Morning Medications")));
    }
}



