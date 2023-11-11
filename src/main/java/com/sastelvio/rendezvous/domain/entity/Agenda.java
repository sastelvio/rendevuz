package com.sastelvio.rendezvous.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "agenda")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "description")
    private String description;
    @NotBlank
    @Column(name = "schedule")
    private LocalDateTime schedule;
    @CreationTimestamp
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    @Column(name = "date_update")
    private LocalDateTime dataUpdate;
    @ManyToOne
    private Patient patient;
}
