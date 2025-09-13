package application.backend.appointment.services;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.repositories.AppointmentRepo;
import application.backend.users.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepo repo;

    public AppointmentService(AppointmentRepo repo) {
        this.repo = repo;
    }

    public void saveAppointment(Appointment appointment) {
        repo.save(appointment);
    }

    public void deleteAppointment(Appointment appointment) {
        repo.delete(appointment);
    }

    public Optional<Appointment> findByAppointment(Appointment appointment) {
        return repo.findById(appointment.getId());
    }

    public List<Appointment> findAppointmentsForUser(User user) {
        return repo.findByAppointerOrAppointee(user, user);
    }

    public List<Appointment> findAll() { return repo.findAll(); }

    public List<Appointment> findAppointmentsForUserAtMonth(User user, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return repo.findAppointmentsForUserInDateRange(user, startDate, endDate);
    }
}
