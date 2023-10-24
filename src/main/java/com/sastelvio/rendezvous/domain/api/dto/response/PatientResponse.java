package com.sastelvio.rendezvous.domain.api.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    private Long id;
    private String firstName;
    private String surname;
    private String socialSecurity;
    private String email;
}
