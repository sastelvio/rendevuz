package com.sastelvio.rendezvous.domain.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {
    private String firstName;
    private String surname;
    private String socialSecurity;
    private String email;
}
