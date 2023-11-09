package com.sastelvio.rendezvous.api.v1.mapper;

import com.sastelvio.rendezvous.api.v1.dto.response.PatientResponse;
import com.sastelvio.rendezvous.api.v1.dto.request.PatientRequest;
import com.sastelvio.rendezvous.domain.entity.Patient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PatientMapper {
    private final ModelMapper mapper;

    public Patient toPatient(PatientRequest request) {
        return mapper.map(request, Patient.class);
    }

    public PatientResponse toPatientResponse(Patient patient) {
        return mapper.map(patient, PatientResponse.class);
    }

    public List<PatientResponse> toPatientResponseList(List<Patient> patients) {
        return patients.stream()
                .map(this::toPatientResponse)
                .collect(Collectors.toList());
    }

}
