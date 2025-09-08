package application.backend.appointment.repositories;

import application.backend.appointment.models.Appointment;
import application.backend.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Appointment> findByAppointerOrAppointee(User appointer, User appointee);

    List<Appointment> findByAppointer(User user);

    List<Appointment> findByAppointee(User user);

}
