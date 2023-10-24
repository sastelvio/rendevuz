package com.sastelvio.rendezvous.domain.service;

import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository repository;

    public Patient save(Patient patient){
        //TODO validate uniqueness of email and social security
        return repository.save(patient);
    }

    public List<Patient> findAll(){
        return repository.findAll();
    }

    public Optional<Patient> findById(Long id){
        return repository.findById(id);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }


}
