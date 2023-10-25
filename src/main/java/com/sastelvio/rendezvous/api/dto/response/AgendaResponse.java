package com.sastelvio.rendezvous.api.dto.response;

import com.sastelvio.rendezvous.domain.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgendaResponse {
    private Long id;
    private String description;
    private LocalDateTime schedule;
    private PatientResponse patientId;
}
