package com.sastelvio.rendezvous.domain.repository;

import com.sastelvio.rendezvous.domain.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment>  findBySchedule(LocalDateTime schedule);
}
