package com.sastelvio.rendezvous.domain.service;

import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.repository.PatientRepository;
import com.sastelvio.rendezvous.exception.BusinessException;
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

    public Patient save(Patient patient) {
        //validate uniqueness of email
        boolean emailExists = false;
        Optional<Patient> optPatientFindByEmail = repository.findByEmail(patient.getEmail());
        if (optPatientFindByEmail.isPresent()) {
            if (!optPatientFindByEmail.get().getId().equals(patient.getId())) {
                emailExists = true;
            }
        }
        if (emailExists) {
            throw new BusinessException("There is a record using this Email.");
        }


        //validate uniqueness of social security
        boolean socialSecurityExists = false;
        Optional<Patient> optPatientFindBySocialSecurity = repository.findBySocialSecurity(patient.getSocialSecurity());
        if (optPatientFindBySocialSecurity.isPresent()) {
            if (!optPatientFindBySocialSecurity.get().getId().equals(patient.getId())) {
                socialSecurityExists = true;
            }
        }
        if (socialSecurityExists) {
            throw new BusinessException("There is a record using this Social Security.");
        }

        return repository.save(patient);
    }

    public List<Patient> findAll() { return repository.findAll(); }

    public Optional<Patient> findById(Long id) { return repository.findById(id); }

    public Patient update(Long id, Patient patient) {
        Optional<Patient> optPatient = this.findById(id);
        if (optPatient.isEmpty()) {
            throw new BusinessException("No patient found to update!");
        }
        patient.setId(id);
        return save(patient);
    }

    public void delete(Long id) { repository.deleteById(id); }


}
