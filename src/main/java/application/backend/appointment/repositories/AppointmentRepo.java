package application.backend.appointment.repositories;

import application.backend.appointment.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
