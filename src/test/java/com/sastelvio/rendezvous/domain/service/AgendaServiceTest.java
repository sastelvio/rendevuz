package com.sastelvio.rendezvous.domain.service;

import com.sastelvio.rendezvous.domain.entity.Appointment;
import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.repository.AppointmentRepository;
import com.sastelvio.rendezvous.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @InjectMocks
    AppointmentService appointmentService;
    @Mock
    PatientService patientService;
    @Mock
    AppointmentRepository appointmentRepository;

    @Captor
    ArgumentCaptor<Appointment> appointmentCaptor;

    @Test
    @DisplayName("Must save an Appointment Successfully")
    public void testSaveAppointmentSuccessfully() {
        // Arrange (Setup/Given)
        Appointment appointment = new Appointment();
        appointment.setSchedule(LocalDateTime.now());

        Patient patient = new Patient();
        patient.setId(1L);
        appointment.setPatient(patient); // Set the patient on the appointment

        when(patientService.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findBySchedule(any())).thenReturn(Optional.empty());
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        // Act (Test/When)
        Appointment savedAppointment = appointmentService.save(appointment);

        // Assert (Validation/Then)
        Mockito.verify(patientService, times(1)).findById(1L);
        Mockito.verify(appointmentRepository, times(1)).findBySchedule(any());
        Mockito.verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    @DisplayName("Must NOT save an Appointment without a Patient")
    public void testSaveAppointmentPatientNotFound() {
        // Arrange (Setup/Given)
        Patient patient = new Patient();
        patient.setId(1L);  // Set a valid patient ID

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);  // Set the patient on the appointment
        appointment.setSchedule(LocalDateTime.now());

        when(patientService.findById(1L)).thenReturn(Optional.empty());
        appointment.setSchedule(LocalDateTime.now());


        // Act (Test/When) & Assert (Validation/Then)
        assertThrows(BusinessException.class, () -> appointmentService.save(appointment));
    }

    @Test
    @DisplayName("Must NOT save an Appointment if there is another one already saved using the same Schedule time")
    public void testSaveAppointmentDuplicateSchedule() {
        // Arrange (Setup/Given)
        Patient patient = new Patient();
        patient.setId(1L);  // Set a valid patient ID

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);  // Set the patient on the appointment
        appointment.setSchedule(LocalDateTime.now());

        when(patientService.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findBySchedule(any())).thenReturn(Optional.of(new Appointment()));


        // Act (Test/When) & // Assert (Validation/Then)
        assertThrows(BusinessException.class, () -> appointmentService.save(appointment));
    }

    @Test
    @DisplayName("Must update an Appointment Successfully")
    public void testUpdateAppointmentSuccessfully() {
        // Arrange (Setup/Given)
        long appointmentId = 1L;

        // Create a Patient object
        Patient patient = new Patient();
        patient.setId(1L);

        // Create an Appointment object and set the Patient
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setSchedule(LocalDateTime.now());
        appointment.setPatient(patient); // Associate the appointment with the patient

        when(patientService.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        // Act (Test/When)
        Appointment updatedAppointment = appointmentService.update(appointmentId, appointment);

        // Assert (Validation/Then)
        Mockito.verify(appointmentRepository, times(1)).findById(appointmentId);
        Mockito.verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    @DisplayName("Could NOT update an Appointment if it is not found/saved into database")
    public void testUpdateAppointmentNotFound() {
        //setup = Arrange/Given
        long appointmentId = 1L;
        Appointment appointment = new Appointment();

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        // Act (Test/When) & validation = Assertion/Then
        assertThrows(BusinessException.class, () -> appointmentService.update(appointmentId, appointment));
    }

    @Test
    @DisplayName("Must delete an Appointment Successfully")
    public void testDeleteAppointment() {
        //setup = Arrange/Given
        long appointmentId = 1L;

        // Act (Test/When)
        appointmentService.delete(appointmentId);

        //validation = Assertion/Then
        Mockito.verify(appointmentRepository, times(1)).deleteById(appointmentId);
    }

    @Test
    @DisplayName("Must find all the Appointments Successfully")
    public void testFindAllAppointments() {
        //setup = Arrange/Given
        List<Appointment> appointments = List.of(new Appointment());

        when(appointmentRepository.findAll()).thenReturn(appointments);

        // Act (Test/When)
        List<Appointment> foundAppointments = appointmentService.findAll();

        //validation = Assertion/Then
        Mockito.verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Must find an Appointment by it's ID Successfully")
    public void testFindAppointmentById() {
        //setup = Arrange/Given
        Appointment appointment = new Appointment();
        appointment.setId(1L);

        when(appointmentRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));

        // Act (Test/When)
        Optional<Appointment> foundAppointment = appointmentService.findById(appointment.getId());

        //validation = Assertion/Then
        Mockito.verify(appointmentRepository, times(1)).findById(appointment.getId());
    }

}