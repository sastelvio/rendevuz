package com.sastelvio.rendezvous.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sastelvio.rendezvous.api.v1.dto.request.PatientRequest;
import com.sastelvio.rendezvous.api.v1.dto.response.PatientResponse;
import com.sastelvio.rendezvous.api.v1.mapper.PatientMapper;
import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.repository.PatientRepository;
import com.sastelvio.rendezvous.domain.service.PatientService;
import com.sastelvio.rendezvous.exception.BusinessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class PatientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService service;

    @MockBean
    private PatientMapper mapper;

    @Autowired
    PatientRepository repository;

    @BeforeEach
    void setUp() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("Mary");
        patient.setSurname("Smith");
        patient.setEmail("marysmith@mail.com");
        patient.setSocialSecurity("847385948736234");
        repository.save(patient);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void savePatient_ReturnsCreatedStatus() throws Exception {
        // Arrange
        PatientRequest request = new PatientRequest(/* provide valid request data */);
        PatientResponse expectedResponse = new PatientResponse(/* provide expected response data */);

        given(mapper.toPatient(any())).willReturn(new Patient());
        given(service.save(any())).willReturn(new Patient());
        given(mapper.toPatientResponse(any())).willReturn(expectedResponse);

        // Act & Assert
        mockMvc.perform(post("/api/v1/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.property").value("expectedValue"));
    }

    @Test
    void findAllPatients_ReturnsListOfPatients() throws Exception {
        // Arrange
        List<Patient> patients = service.findAll();
        List<PatientResponse> expectedResponses = mapper.toPatientResponseList(patients);

        given(service.findAll()).willReturn(patients);
        given(mapper.toPatientResponseList(any())).willReturn(expectedResponses);

        // Act & Assert
        mockMvc.perform(get("/api/v1/patient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(patients.size())))
                .andDo(print());
    }

    @Test
    void findPatientById_ExistingPatient_ReturnsOkStatus() throws Exception {
        // Arrange
        Long patientId = 1L;
        PatientResponse expectedResponse = new PatientResponse(/* provide expected response data */);

        given(service.findById(patientId)).willReturn(Optional.of(new Patient()));
        given(mapper.toPatientResponse(any())).willReturn(expectedResponse);

        // Act & Assert
        mockMvc.perform(get("/api/v1/patient/{id}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.property").value("expectedValue"));
    }

    @Test
    void findPatientById_NonExistingPatient_ReturnsNotFoundStatus() throws Exception {
        // Arrange
        Long patientId = 1L;

        given(service.findById(patientId)).willReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/v1/patient/{id}", patientId))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePatient_ExistingPatient_ReturnsOkStatus() throws Exception {
        // Arrange
        Long patientId = 1L;
        PatientRequest request = new PatientRequest(/* provide valid request data */);
        PatientResponse expectedResponse = new PatientResponse(/* provide expected response data */);

        given(mapper.toPatient(any())).willReturn(new Patient());
        given(service.update(eq(patientId), any())).willReturn(new Patient());
        given(mapper.toPatientResponse(any())).willReturn(expectedResponse);

        // Act & Assert
        mockMvc.perform(put("/api/v1/patient/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.property").value("expectedValue"));
    }

    @Test
    void updatePatient_NonExistingPatient_ReturnsNotFoundStatus() throws Exception {
        // Arrange
        Long patientId = 1L;
        PatientRequest request = new PatientRequest(/* provide valid request data */);

        given(service.update(eq(patientId), any())).willThrow(new BusinessException("No patient found to update!"));

        // Act & Assert
        mockMvc.perform(put("/api/v1/patient/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePatient_ExistingPatient_ReturnsNoContentStatus() throws Exception {
        // Arrange
        Long patientId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/v1/patient/{id}", patientId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePatient_NonExistingPatient_ReturnsNotFoundStatus() throws Exception {
        // Arrange
        Long patientId = 1L;

        given(service.findById(patientId)).willReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/api/v1/patient/{id}", patientId))
                .andExpect(status().isNotFound());
    }

    @Test
    void savePatient_ValidationErrors_ReturnsBadRequest() throws Exception {
        // Arrange
        PatientRequest request = new PatientRequest(/* provide request data with validation errors */);

        // Act & Assert
        mockMvc.perform(post("/api/v1/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePatient_ValidationErrors_ReturnsBadRequest() throws Exception {
        // Arrange
        Long patientId = 1L;
        PatientRequest request = new PatientRequest(/* provide request data with validation errors */);

        // Act & Assert
        mockMvc.perform(put("/api/v1/patient/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findPatientById_InvalidIdFormat_ReturnsBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/patient/{id}", "invalidId"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void nonExistentEndpoint_ReturnsNotFound() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/patient/nonexistent"))
                .andExpect(status().isNotFound());
    }

    @Test
    void savePatient_ReturnsCorrectResponseFormat() throws Exception {
        // Arrange
        PatientRequest request = new PatientRequest(/* provide valid request data */);

        // Act & Assert
        mockMvc.perform(post("/api/v1/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.property").value("expectedValue"))
                .andExpect(jsonPath("$.otherProperty").exists());
    }


}
