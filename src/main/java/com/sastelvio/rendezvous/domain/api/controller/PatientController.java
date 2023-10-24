package com.sastelvio.rendezvous.domain.api.controller;

import com.sastelvio.rendezvous.domain.api.dto.request.PatientRequest;
import com.sastelvio.rendezvous.domain.api.dto.response.PatientResponse;
import com.sastelvio.rendezvous.domain.api.mapper.PatientMapper;
import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService service;
    private final PatientMapper mapper;

    @PostMapping
    public ResponseEntity<PatientResponse> save(@RequestBody PatientRequest request){
        Patient patient = PatientMapper.toPatient(request);
        Patient save = service.save(patient);
        PatientResponse patientResponse = mapper.toPatientResponse(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientResponse);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> findAll(){
        List<PatientResponse> patientResponse = service.findAll()
                .stream()
                .map(mapper::toPatientResponse)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(patientResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> findById(@PathVariable Long id){
        return service.findById(id)
                .map(mapper::toPatientResponse)
                .map(patientResponse -> ResponseEntity.status(HttpStatus.OK).body(patientResponse))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<PatientResponse> update(@PathVariable Long id, @RequestBody PatientRequest request){
        Patient patient = PatientMapper.toPatient(request);
        Patient save = service.update(id, patient);
        PatientResponse patientResponse = mapper.toPatientResponse(save);
        return ResponseEntity.status(HttpStatus.OK).body(patientResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
