package com.sastelvio.rendezvous.api.v1.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {
    @NotBlank(message = "First Name is required!")
    private String firstName;
    @NotBlank(message = "Surname is required!")
    private String surname;
    @NotBlank(message = "Social Security is required!")
    private String socialSecurity;
    @NotBlank(message = "Email is required!")
    @Email
    private String email;
}
