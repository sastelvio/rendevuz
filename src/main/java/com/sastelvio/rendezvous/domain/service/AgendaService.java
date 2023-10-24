package com.sastelvio.rendezvous.domain.service;

import com.sastelvio.rendezvous.domain.entity.Agenda;
import com.sastelvio.rendezvous.domain.entity.Patient;
import com.sastelvio.rendezvous.domain.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AgendaService {
    private final AgendaRepository repository;

    public Agenda save(Agenda agenda){
        //TODO validate uniqueness of scheduled time
        return repository.save(agenda);
    }

    public List<Agenda> findAll(){
        return repository.findAll();
    }

    public Optional<Agenda> findById(Long id){
        return repository.findById(id);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
