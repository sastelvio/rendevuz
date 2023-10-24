package com.sastelvio.rendezvous.domain.repository;

import com.sastelvio.rendezvous.domain.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findBySocialSecurity(String socialSecurity);

}
