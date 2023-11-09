package com.sastelvio.rendezvous.api.v1.controller;

import com.sastelvio.rendezvous.api.v1.dto.response.PatientResponse;
import com.sastelvio.rendezvous.api.v1.dto.request.PatientRequest;
import com.sastelvio.rendezvous.api.v1.mapper.PatientMapper;
import com.sastelvio.rendezvous.api.v1.openapi.PatientSpringDoc;
import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/patient")
public class PatientController implements PatientSpringDoc {
    private final PatientService service;
    private final PatientMapper mapper;

    @PostMapping
    public ResponseEntity<PatientResponse> save(@Valid @RequestBody PatientRequest request) {
        Patient patient = mapper.toPatient(request);
        Patient save = service.save(patient);
        PatientResponse patientResponse = mapper.toPatientResponse(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientResponse);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> findAll() {
        List<Patient> patients = service.findAll();
        List<PatientResponse> patientsResponse = mapper.toPatientResponseList(patients);
        return ResponseEntity.status(HttpStatus.OK).body(patientsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> findById(@PathVariable Long id) {
        Optional<Patient> optPatient = service.findById(id);
        if (optPatient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toPatientResponse(optPatient.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> update(@PathVariable Long id, @Valid @RequestBody PatientRequest request) {
        Patient patient = mapper.toPatient(request);
        Patient save = service.update(id, patient);
        PatientResponse patientResponse = mapper.toPatientResponse(save);
        return ResponseEntity.status(HttpStatus.OK).body(patientResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
