package com.sastelvio.rendezvous.api.v1.openapi;

import com.sastelvio.rendezvous.api.v1.dto.request.PatientRequest;
import com.sastelvio.rendezvous.api.v1.dto.response.PatientResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Patient")
public interface PatientSpringDoc {
    @Operation(description = "To save a new patient")
    ResponseEntity<PatientResponse> save(@Valid @RequestBody PatientRequest request);

    @Operation(description = "To list all the patients")
    ResponseEntity<List<PatientResponse>> findAll();

    @Operation(description = "To find a patient by its ID")
    ResponseEntity<PatientResponse> findById(@PathVariable Long id);

    @Operation(description = "To update a patient by its ID")
    ResponseEntity<PatientResponse> update(@PathVariable Long id, @Valid @RequestBody PatientRequest request);

    @Operation(description = "To delete a patient by its ID")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
