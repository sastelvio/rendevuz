package com.sastelvio.rendevuz.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "agenda")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "schedule")
    private LocalDateTime schedule;
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    @ManyToOne
    private Patient patient;


}
