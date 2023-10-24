package com.sastelvio.rendezvous.domain.api.mapper;

import com.sastelvio.rendezvous.domain.api.dto.request.PatientRequest;
import com.sastelvio.rendezvous.domain.api.dto.response.PatientResponse;
import com.sastelvio.rendezvous.domain.entity.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientMapper {
    public static Patient toPatient(PatientRequest patientRequest){
        Patient patient = new Patient();
        patient.setFirstName(patientRequest.getFirstName());
        patient.setSurname(patientRequest.getSurname());
        patient.setEmail(patientRequest.getEmail());
        patient.setSocialSecurity(patientRequest.getSocialSecurity());
        return patient;
    }

    public PatientResponse toPatientResponse(Patient patient){
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setId(patient.getId());
        patientResponse.setFirstName(patient.getFirstName());
        patientResponse.setSurname(patient.getSurname());
        patientResponse.setEmail(patient.getEmail());
        patientResponse.setSocialSecurity(patient.getSocialSecurity());
        return patientResponse;
    }
}
