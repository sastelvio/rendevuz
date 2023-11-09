package com.sastelvio.rendezvous.api.v1.controller;

import com.sastelvio.rendezvous.api.v1.dto.request.AgendaRequest;
import com.sastelvio.rendezvous.api.v1.dto.response.AgendaResponse;
import com.sastelvio.rendezvous.api.v1.mapper.AgendaMapper;
import com.sastelvio.rendezvous.domain.entity.Agenda;
import com.sastelvio.rendezvous.domain.service.AgendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/agenda")
public class AgendaController {
    private final AgendaService service;
    private final AgendaMapper mapper;

    @PostMapping
    public ResponseEntity<AgendaResponse> save(@Valid @RequestBody AgendaRequest request) {
        Agenda agenda = mapper.toAgenda(request);
        Agenda save = service.save(agenda);
        AgendaResponse agendaResponse = mapper.toAgendaResponse(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaResponse);
    }

    @GetMapping
    public ResponseEntity<List<AgendaResponse>> findAll() {
        List<Agenda> agendas = service.findAll();
        List<AgendaResponse> agendasResponse = mapper.toAgendaResponseList(agendas);
        return ResponseEntity.status(HttpStatus.OK).body(agendasResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaResponse> findById(@PathVariable Long id) {
        Optional<Agenda> optAgenda = service.findById(id);
        if (optAgenda.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toAgendaResponse(optAgenda.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaResponse> update(@PathVariable Long id, @Valid @RequestBody AgendaRequest request) {
        Agenda patient = mapper.toAgenda(request);
        Agenda save = service.update(id, patient);
        AgendaResponse agendaResponse = mapper.toAgendaResponse(save);
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
