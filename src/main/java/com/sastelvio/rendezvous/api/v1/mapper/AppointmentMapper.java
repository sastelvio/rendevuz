package com.sastelvio.rendezvous.api.v1.mapper;

import com.sastelvio.rendezvous.api.v1.dto.request.AppointmentRequest;
import com.sastelvio.rendezvous.api.v1.dto.response.AppointmentResponse;
import com.sastelvio.rendezvous.domain.entity.Appointment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppointmentMapper {
    private final ModelMapper mapper;
    private final PatientMapper patientMapper;

    public Appointment toAppointment(AppointmentRequest request) {
        return mapper.map(request, Appointment.class);
    }

    public AppointmentResponse toAppointmentResponse(Appointment appointment) {
        AppointmentResponse response = mapper.map(appointment, AppointmentResponse.class);

        // Map the patient to a PatientResponse
        if (appointment.getPatient() != null) {
            response.setPatientResponse(patientMapper.toPatientResponse(appointment.getPatient()));
        }

        return response;
    }

    public List<AppointmentResponse> toAppointmentResponseList(List<Appointment> appointments) {
        return appointments.stream()
                .map(this::toAppointmentResponse)
                .collect(Collectors.toList());
    }
}
