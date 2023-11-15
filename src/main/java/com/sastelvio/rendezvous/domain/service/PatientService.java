package com.sastelvio.rendezvous.domain.service;

import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.repository.PatientRepository;
import com.sastelvio.rendezvous.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository repository;

    public Patient save(Patient patient) {
        log.info("saving patient...");
        // Validate uniqueness of email
        boolean emailExists = false;
        Optional<Patient> optPatientFindByEmail = repository.findByEmail(patient.getEmail());
        if (optPatientFindByEmail.isPresent()) {
            Long patientId = optPatientFindByEmail.get().getId();
            if (patientId != null && patientId.equals(patient.getId())) {
                emailExists = true;
            }
        }

        if (emailExists) {
            log.error("There is a record using this Email!");
            throw new BusinessException("There is a record using this Email!");
        }

        // Validate uniqueness of social security
        boolean socialSecurityExists = false;
        Optional<Patient> optPatientFindBySocialSecurity = repository.findBySocialSecurity(patient.getSocialSecurity());
        if (optPatientFindBySocialSecurity.isPresent()) {
            Long patientId = optPatientFindBySocialSecurity.get().getId();
            if (patientId != null && patientId.equals(patient.getId())) {
                socialSecurityExists = true;
            }
        }

        if (socialSecurityExists) {
            log.error("There is a record using this Social Security!");
            throw new BusinessException("There is a record using this Social Security!");
        }
        return repository.save(patient);
    }

    public List<Patient> findAll() { log.info("listing all patients..."); return repository.findAll(); }

    public Optional<Patient> findById(Long id) { log.info("finding patient: {}...", id); return repository.findById(id); }

    public Patient update(Long id, Patient patient) {
        log.info("updating patient: {}...", id);
        Optional<Patient> optPatient = this.findById(id);
        if (optPatient.isEmpty()) {
            log.error("No patient found to update!");
            throw new BusinessException("No patient found to update!");
        }
        patient.setId(id);
        return save(patient);
    }

    public void delete(Long id) { log.info("deleting patient: {}", id); repository.deleteById(id); }

}
