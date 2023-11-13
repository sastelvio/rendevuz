package com.sastelvio.rendezvous.api.v1.openapi;

import com.sastelvio.rendezvous.api.v1.dto.request.AppointmentRequest;
import com.sastelvio.rendezvous.api.v1.dto.response.AppointmentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Appointment")
public interface AppointmentSpringDoc {
    @Operation(description = "To save a new appointment")
    ResponseEntity<AppointmentResponse> save(@Valid @RequestBody AppointmentRequest request);

    @Operation(description = "To list all the appointments")
    ResponseEntity<List<AppointmentResponse>> findAll();

    @Operation(description = "To find an appointment by its ID")
    ResponseEntity<AppointmentResponse> findById(@PathVariable Long id);

    @Operation(description = "To update an appointment by its ID")
    ResponseEntity<AppointmentResponse> update(@PathVariable Long id, @Valid @RequestBody AppointmentRequest request);

    @Operation(description = "To delete an appointment by its ID")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
