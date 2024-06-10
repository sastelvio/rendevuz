package com.sastelvio.rendezvous.domain.service;

import com.sastelvio.rendezvous.domain.entity.Appointment;
import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.repository.AppointmentRepository;
import com.sastelvio.rendezvous.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository repository;
    private final PatientService patientService;

    public Appointment save(Appointment appointment) {
        log.info("saving appointment...");
        // check if appointment exists
        // TODO BUG 1: This could generate a bug at certain point, because it trying to get patient without checking if it exists
        Optional<Patient> patientInAppointment = patientService.findById(appointment.getPatient().getId());
        if (patientInAppointment.isEmpty()) {
            log.error("Patient not found!");
            throw new BusinessException("Patient not found!");
        }
    
        // if it's a new appointment, check if the schedule already exists
        if (appointment.getId() == null) {
            Optional<Appointment> bySchedule = repository.findBySchedule(appointment.getSchedule());
            if (bySchedule.isPresent()) {
                log.error("This date and time already has an appointment!");
                throw new BusinessException("This date and time already has an appointment!");
            }
        }
    
        appointment.setPatient(patientInAppointment.get());
    
        appointment.setDateCreation(LocalDateTime.now());
        appointment.setDateUpdate(LocalDateTime.now());
        return repository.save(appointment);
    }
    

    public List<Appointment> findAll() {
        log.info("listing all appointments..."); return repository.findAll();
    }

    public Optional<Appointment> findById(Long id) {
        log.info("finding appointment: {}...", id); return repository.findById(id);
    }

    public Appointment update(Long id, Appointment appointment) {
        log.info("updating appointment: {}...", id);
        Optional<Appointment> optAppointment = this.findById(id);
        if (optAppointment.isEmpty()) {
            log.error("No appointment found to update!");
            throw new BusinessException("No appointment found to update!");
        }
        appointment.setId(id);
        appointment.setDateUpdate(LocalDateTime.now());
        return save(appointment);
    }

    public void delete(Long id) { log.info("deleting appointment: {}", id); repository.deleteById(id); }
}
