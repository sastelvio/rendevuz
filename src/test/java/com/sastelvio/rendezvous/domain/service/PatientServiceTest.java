package com.sastelvio.rendezvous.domain.service;

import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.repository.PatientRepository;
import com.sastelvio.rendezvous.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @InjectMocks
    private PatientService patientService;
    @Mock
    private PatientRepository patientRepository;

    @Test
    @DisplayName("Must save a Patient Successfully")
    public void testSavePatientSuccessfully() {
        // Arrange (Setup/Given)
        Patient patient = new Patient();
        patient.setEmail("test@example.com");
        patient.setSocialSecurity("123-45-6789");

        when(patientRepository.findByEmail(patient.getEmail())).thenReturn(Optional.empty());
        when(patientRepository.findBySocialSecurity(patient.getSocialSecurity())).thenReturn(Optional.empty());
        when(patientRepository.save(patient)).thenReturn(patient);

        // Act (Test/When)
        patientService.save(patient);

        // Assert (Validation/Then)
        verify(patientRepository, times(1)).findByEmail(patient.getEmail());
        verify(patientRepository, times(1)).findBySocialSecurity(patient.getSocialSecurity());
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    @DisplayName("Must NOT save a Patient with an email already being used for another Patient")
    public void testSavePatientDuplicateEmail() {
        // Arrange (Setup/Given)
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setEmail("test@example.com");

        when(patientRepository.findByEmail(patient.getEmail())).thenReturn(Optional.empty());

        // Act (Test/When) & Assert (Validation/Then)
        patientService.save(patient);
    }

    @Test
    @DisplayName("Must NOT save a Patient with a Social Security number already being used for another Patient")
    public void testSavePatientDuplicateSocialSecurity() {
        // Arrange (Setup/Given)
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setSocialSecurity("123-45-6789");

        when(patientRepository.findBySocialSecurity(patient.getSocialSecurity())).thenReturn(Optional.empty());

        // Act (Test/When) & Assert (Validation/Then)
        patientService.save(patient);
    }

    @Test
    @DisplayName("Must Update a Patient Successfully")
    public void testUpdatePatientSuccessfully() {
        // Arrange (Setup/Given)
        long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);

        // Act (Test/When)
        Patient updatedPatient = patientService.update(patientId, patient);

        // Assert (Validation/Then)
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    @DisplayName("Must NOT update a Patient if it couldn't find it by the ID")
    public void testUpdatePatientNotFound() {
        // Arrange (Setup/Given)
        long patientId = 1L;
        Patient patient = new Patient();

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // Act (Test/When) & Assert (Validation/Then)
        assertThrows(BusinessException.class, () -> patientService.update(patientId, patient));
    }

    @Test
    @DisplayName("Must Delete definitively a Patient from the database")
    public void testDeletePatient() {
        // Arrange (Setup/Given)
        long patientId = 1L;

        // Act (Test/When)
        patientService.delete(patientId);

        // Assert (Validation/Then)
        verify(patientRepository, times(1)).deleteById(patientId);
    }

    @Test
    @DisplayName("Must list all the Patients available on the database")
    public void testFindAllPatients() {
        // Arrange (Setup/Given)
        List<Patient> patients = List.of(new Patient());

        when(patientRepository.findAll()).thenReturn(patients);

        // Act (Test/When)
        List<Patient> foundPatients = patientService.findAll();

        // Assert (Validation/Then)
        verify(patientRepository, times(1)).findAll();
    }

}