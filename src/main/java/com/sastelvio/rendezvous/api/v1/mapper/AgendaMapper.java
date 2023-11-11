package com.sastelvio.rendezvous.api.v1.mapper;

import com.sastelvio.rendezvous.api.v1.dto.request.AgendaRequest;
import com.sastelvio.rendezvous.api.v1.dto.response.AgendaResponse;
import com.sastelvio.rendezvous.domain.entity.Agenda;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AgendaMapper {
    private final ModelMapper mapper;
    private final PatientMapper patientMapper;

    public Agenda toAgenda(AgendaRequest request) {
        return mapper.map(request, Agenda.class);
    }

    public AgendaResponse toAgendaResponse(Agenda agenda) {
        AgendaResponse response = mapper.map(agenda, AgendaResponse.class);

        // Map the patient to a PatientResponse
        if (agenda.getPatient() != null) {
            response.setPatientResponse(patientMapper.toPatientResponse(agenda.getPatient()));
        }

        return response;
    }

    public List<AgendaResponse> toAgendaResponseList(List<Agenda> agendas) {
        return agendas.stream()
                .map(this::toAgendaResponse)
                .collect(Collectors.toList());
    }
}
