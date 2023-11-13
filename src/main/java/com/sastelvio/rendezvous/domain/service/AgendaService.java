package com.sastelvio.rendezvous.domain.service;

import com.sastelvio.rendezvous.domain.entity.Appointment;
import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.repository.AppointmentRepository;
import com.sastelvio.rendezvous.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository repository;
    private final PatientService patientService;

    public Appointment save(Appointment appointment) {
        //check if appointment exists
        Optional<Patient> patientInAppointment = patientService.findById(appointment.getPatient().getId());
        if (patientInAppointment.isEmpty()) {
            throw new BusinessException("Patient not found!");
        }

        //TODO fix the bug that allows 2 records in the same schedule
        //validate the scheduled time, one appointment per datetime
        Optional<Appointment> bySchedule = repository.findBySchedule(appointment.getSchedule());
        if (bySchedule.isPresent()) {
            throw new BusinessException("This date and time already has an appointment!");
        }
        appointment.setPatient(patientInAppointment.get());
        //appointment.setSchedule(LocalDateTime.now());
        return repository.save(appointment);
    }

    public List<Appointment> findAll() {
        return repository.findAll();
    }

    public Optional<Appointment> findById(Long id) {
        return repository.findById(id);
    }

    public Appointment update(Long id, Appointment appointment) {
        Optional<Appointment> optAppointment = this.findById(id);
        if (optAppointment.isEmpty()) {
            throw new BusinessException("No appointment found to update!");
        }
        appointment.setId(id);
        return save(appointment);
    }

    public void delete(Long id) { repository.deleteById(id); }
}
