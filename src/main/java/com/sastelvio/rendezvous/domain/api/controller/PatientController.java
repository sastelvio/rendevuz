package com.sastelvio.rendezvous.domain.api.controller;

import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
