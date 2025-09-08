package application.backend.appointment.services;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.repositories.AppointmentRepo;
import application.backend.users.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepo repo;

    public AppointmentService(AppointmentRepo repo) {
        this.repo = repo;
    }

    public void saveAppointment(Appointment appointment) {
        repo.save(appointment);
    }

    public List<Appointment> getAppointmentsForMonth(YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return repo.findByDateBetween(startDate, endDate);
    }

    public List<Appointment> getAppointmentsForUser(User user) {
        return repo.findByAppointerOrAppointee(user, user);
    }

    public List<Appointment> getAppointmentsForAppointer(User user) {
        return repo.findByAppointer(user);
    }

    public List<Appointment> getAppointmentsForAppointee(User user) {
        return repo.findByAppointee(user);
    }

}
