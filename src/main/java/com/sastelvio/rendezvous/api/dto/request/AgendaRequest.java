package com.sastelvio.rendezvous.api.dto.request;

import com.sastelvio.rendezvous.domain.entity.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgendaRequest {
    @NotBlank(message = "Description is required!")
    private String description;
    @NotNull
    @Future
    private LocalDateTime schedule;
    @NotNull
    private Patient patient;
}
