package com.sastelvio.rendezvous.api.v1.openapi;

import com.sastelvio.rendezvous.api.v1.dto.request.AgendaRequest;
import com.sastelvio.rendezvous.api.v1.dto.response.AgendaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Agenda")
public interface AgendaSpringDoc {
    @Operation(description = "To save a new agenda")
    ResponseEntity<AgendaResponse> save(@Valid @RequestBody AgendaRequest request);

    @Operation(description = "To list all the agendas")
    ResponseEntity<List<AgendaResponse>> findAll();

    @Operation(description = "To find an agenda by its ID")
    ResponseEntity<AgendaResponse> findById(@PathVariable Long id);

    @Operation(description = "To update an agenda by its ID")
    ResponseEntity<AgendaResponse> update(@PathVariable Long id, @Valid @RequestBody AgendaRequest request);

    @Operation(description = "To delete an agenda by its ID")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
