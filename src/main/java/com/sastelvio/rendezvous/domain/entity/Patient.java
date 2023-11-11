package com.sastelvio.rendezvous.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "first_name")
    private String firstName;
    @NotBlank
    @Column(name = "surname")
    private String surname;
    @NotBlank
    @Column(name = "social_security")
    private String socialSecurity;
    @NotBlank
    @Column(name = "email")
    private String email;

}
