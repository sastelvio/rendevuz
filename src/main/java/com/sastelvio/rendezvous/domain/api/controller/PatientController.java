package com.sastelvio.rendezvous.domain.api.controller;

import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService service;

    @PostMapping
    public ResponseEntity<Patient> save(@RequestBody Patient patient){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(patient));
    }

    @GetMapping
    public ResponseEntity<List<Patient>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> findById(@PathVariable Long id){
        Optional<Patient> optPatient = service.findById(id);
        return optPatient.map(patient -> ResponseEntity.status(HttpStatus.OK).body(patient)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Patient> update(@RequestBody Patient patient){
        return ResponseEntity.status(HttpStatus.OK).body(service.save(patient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
