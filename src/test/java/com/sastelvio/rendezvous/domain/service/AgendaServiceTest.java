package com.sastelvio.rendezvous.domain.service;

import com.sastelvio.rendezvous.domain.entity.Appointment;
import com.sastelvio.rendezvous.domain.entity.Patient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {

    AppointmentService service;

    @Test
    @DisplayName("Must save an Appointment Successfully")
    void saveSuccessfully() {
        //setup = Arrange
        Appointment appointment = new Appointment();
        appointment.setSchedule(LocalDateTime.now());
        appointment.setDescription("Description of the Appointment");

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("Patient");

        appointment.setPatient(patient);

        //test = Action

        //validation = Assertion
    }
}