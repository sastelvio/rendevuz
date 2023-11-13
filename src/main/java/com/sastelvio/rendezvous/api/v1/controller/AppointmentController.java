package com.sastelvio.rendezvous.api.v1.controller;

import com.sastelvio.rendezvous.api.v1.dto.request.AppointmentRequest;
import com.sastelvio.rendezvous.api.v1.dto.response.AppointmentResponse;
import com.sastelvio.rendezvous.api.v1.mapper.AppointmentMapper;
import com.sastelvio.rendezvous.api.v1.openapi.AppointmentSpringDoc;
import com.sastelvio.rendezvous.domain.entity.Appointment;
import com.sastelvio.rendezvous.domain.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController implements AppointmentSpringDoc {
    private final AppointmentService service;
    private final AppointmentMapper mapper;

    @PostMapping
    public ResponseEntity<AppointmentResponse> save(@Valid @RequestBody AppointmentRequest request) {
        Appointment appointment = mapper.toAppointment(request);
        Appointment save = service.save(appointment);
        AppointmentResponse appointmentResponse = mapper.toAppointmentResponse(save);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentResponse);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> findAll() {
        List<Appointment> appointments = service.findAll();
        List<AppointmentResponse> appointmentsResponse = mapper.toAppointmentResponseList(appointments);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> findById(@PathVariable Long id) {
        Optional<Appointment> optAppointment = service.findById(id);
        if (optAppointment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toAppointmentResponse(optAppointment.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> update(@PathVariable Long id, @Valid @RequestBody AppointmentRequest request) {
        Appointment patient = mapper.toAppointment(request);
        Appointment save = service.update(id, patient);
        AppointmentResponse appointmentResponse = mapper.toAppointmentResponse(save);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
