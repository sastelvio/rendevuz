package com.sastelvio.rendezvous.domain.service;

import com.sastelvio.rendezvous.domain.entity.Agenda;
import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.repository.AgendaRepository;
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
public class AgendaService {
    private final AgendaRepository repository;
    private final PatientService patientService;

    public Agenda save(Agenda agenda) {
        //check if agenda exists
        Optional<Patient> patientInAgenda = patientService.findById(agenda.getPatient().getId());
        if (patientInAgenda.isEmpty()) {
            throw new BusinessException("Patient not found!");
        }

        //TODO fix the bug that allows 2 records in the same schedule
        //validate the scheduled time, one appointment per datetime
        Optional<Agenda> bySchedule = repository.findBySchedule(agenda.getSchedule());
        if (bySchedule.isPresent()) {
            throw new BusinessException("This date and time already has an appointment!");
        }
        agenda.setPatient(patientInAgenda.get());
        //agenda.setSchedule(LocalDateTime.now());
        return repository.save(agenda);
    }

    public List<Agenda> findAll() {
        return repository.findAll();
    }

    public Optional<Agenda> findById(Long id) {
        return repository.findById(id);
    }

    public Agenda update(Long id, Agenda agenda) {
        Optional<Agenda> optAgenda = this.findById(id);
        if (optAgenda.isEmpty()) {
            throw new BusinessException("No agenda found to update!");
        }
        agenda.setId(id);
        return save(agenda);
    }

    public void delete(Long id) { repository.deleteById(id); }
}
