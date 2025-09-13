package application.backend.appointment.services;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.models.AppointmentStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class AppointmentCleanupService {

    private final AppointmentService appointmentService;

    public AppointmentCleanupService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void cleanupExpiredAppointments() {
        System.out.println("Running scheduled appointment cleanup...");

        List<Appointment> allAppointments = appointmentService.findAll();
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();

        for (Appointment appointment : allAppointments) {
            switch (appointment.getStatus()) {
                case PENDING -> handlePendingAppointment(appointment, today, now);
                case ACCEPTED -> handleAcceptedAppointment(appointment, today);
                case REJECTED -> handleRejectedAppointment(appointment, now);
                case CANCELED -> handleCanceledAppointment(appointment, now);
                case UNRESOLVED -> handleUnresolvedAppointment(appointment, now);
            }
        }
     }

    private void handlePendingAppointment(Appointment appointment, LocalDate today, LocalDateTime now) {
        boolean hasPassedScheduledDate = appointment.getDate().isBefore(today);
        if (hasPassedScheduledDate) {
            appointment.setStatus(AppointmentStatus.UNRESOLVED);
            appointment.setStatusChangeTime(now);
            appointmentService.saveAppointment(appointment);
            System.out.println("Marked PENDING appointment " + appointment.getId() + " as UNRESOLVED.");
        }
    }

    private void handleAcceptedAppointment(Appointment appointment, LocalDate today) {
        boolean hasBeenAcceptedForOverADay = appointment.getDate().isBefore(today.minusDays(1));
        if (hasBeenAcceptedForOverADay) {
            appointmentService.deleteAppointment(appointment);
            System.out.println("Deleted ACCEPTED appointment " + appointment.getId());
        }
    }

    private void handleRejectedAppointment(Appointment appointment, LocalDateTime now) {
        boolean hasBeenRejectedForOverADay = appointment.getStatusChangeTime() != null &&
            appointment.getStatusChangeTime().isBefore(now.minusDays(1));
        if (hasBeenRejectedForOverADay) {
            appointmentService.deleteAppointment(appointment);
            System.out.println("Deleted REJECTED appointment " + appointment.getId());
        }
    }

    private void handleCanceledAppointment(Appointment appointment, LocalDateTime now) {
        boolean hasBeenCanceledForOverAnHour = appointment.getStatusChangeTime() != null &&
            appointment.getStatusChangeTime().isBefore(now.minusHours(1));
        if (hasBeenCanceledForOverAnHour) {
            appointmentService.deleteAppointment(appointment);
            System.out.println("Deleted CANCELED appointment " + appointment.getId());
        }
    }

    private void handleUnresolvedAppointment(Appointment appointment, LocalDateTime now) {
        boolean hasBeenUnresolvedForOverADay = appointment.getStatusChangeTime() != null &&
            appointment.getStatusChangeTime().isBefore(now.minusDays(1));
        if (hasBeenUnresolvedForOverADay) {
            appointmentService.deleteAppointment(appointment);
            System.out.println("Deleted UNRESOLVED appointment " + appointment.getId());
        }
    }

}
