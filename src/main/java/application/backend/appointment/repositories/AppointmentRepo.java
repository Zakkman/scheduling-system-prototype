package application.backend.appointment.repositories;

import application.backend.appointment.models.Appointment;
import application.backend.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    List<Appointment> findByAppointerOrAppointee(User appointer, User appointee);

    @Query("SELECT a FROM Appointment a " +
        "WHERE (a.appointer = :user OR a.appointee = :user) " +
        "AND a.date BETWEEN :startDate AND :endDate")
    List<Appointment> findAppointmentsForUserInDateRange(
        @Param("user") User user,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
}
