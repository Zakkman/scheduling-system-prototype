package application.backend.appointment.services;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.repositories.AppointmentRepo;
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

    public List<Appointment> getAppointmentsForMonth(YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return repo.findByDateBetween(startDate, endDate);
    }

    //TODO: make the get appointments methods

    public void saveAppointment(Appointment appointment) {
        repo.save(appointment);
    }

}
