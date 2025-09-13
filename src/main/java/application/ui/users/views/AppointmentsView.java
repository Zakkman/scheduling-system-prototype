package application.ui.users.views;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.models.AppointmentStatus;
import application.backend.appointment.services.AppointmentService;
import application.backend.security.CustomUserDetails;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.backend.users.services.UserService;
import application.ui.calendar.Calendar;
import application.ui.users.components.cards.AppointmentCard;
import application.ui.users.components.cards.profiles.UserProfile;
import application.ui.users.components.dialogs.SchedulingDialog;
import application.ui.users.components.forms.ManageAppointmentForm;
import application.ui.users.components.forms.SchedulingForm;
import application.ui.users.components.grids.AppointmentCardGrid;
import application.ui.users.components.dialogs.ManageAppointmentDialog;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

public abstract class AppointmentsView extends SchedulableView implements BeforeEnterObserver {

    private final AppointmentCardGrid appointmentCardGrid;
    private final ConfirmDialog confirmCancelDialog;
    protected final ManageAppointmentDialog manageAppointmentDialog;

    public AppointmentsView(AppointmentService appointmentService,
                            UserService userService,
                            TeacherService teacherService,
                            StudentService studentService) {
        super(appointmentService, userService);
        this.appointmentCardGrid = new AppointmentCardGrid(teacherService, studentService, this.appointmentService);
        this.confirmCancelDialog = new ConfirmDialog();
        this.manageAppointmentDialog = new ManageAppointmentDialog();

        add(appointmentCardGrid);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setFlexGrow(1, appointmentCardGrid);

        configureConfirmCancelDialog();
        configureManageAppointmentDialog();
    }

    private void configureConfirmCancelDialog() {
        confirmCancelDialog.setHeader("Cancel appointment?");
        confirmCancelDialog.setText("Are you sure you want to cancel this appointment?");
        confirmCancelDialog.setCancelable(true);
        confirmCancelDialog.setCancelText("Back");
        confirmCancelDialog.setConfirmText("Cancel");
        confirmCancelDialog.setConfirmButtonTheme("error primary");
    }

    private void configureManageAppointmentDialog() {
        manageAppointmentDialog.addDialogCloseActionListener(close ->
            handleManageAppointmentDialogClose(null)
        );
        configureManageAppointmentFormEvents();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            currentUser = ((CustomUserDetails) principal).getUser();
            appointmentCardGrid.setAuthenticatedUser(currentUser);
            schedulingDialog = new SchedulingDialog(currentUser);

            configureScheduleDialog();
            configureGridSelect();
        } else {
            event.rerouteTo("login");
            Notification.show("Your session has expired. Please log in again.");
        }
    }

    @Override
    protected void configureGridSelect() {
        appointmentCardGrid.getGrid().asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Appointment selectedAppointment = event.getValue();
                UserProfile<?> userProfile = appointmentCardGrid.createUserProfile(selectedAppointment);

                if (userProfile == null) {
                    return;
                }

                configureAvailableButtons(selectedAppointment);

                AppointmentCard appointmentCard = new AppointmentCard(selectedAppointment, currentUser, userProfile);
                manageAppointmentDialog.getForm().setAppointmentCard(appointmentCard);
                manageAppointmentDialog.open();
            }
        });
    }

    protected void handleManageAppointmentDialogClose(ManageAppointmentForm.BackEvent event) {
        appointmentCardGrid.getGrid().asSingleSelect().clear();
        appointmentCardGrid.updateGrid();
        manageAppointmentDialog.getForm().clear();
        manageAppointmentDialog.close();
    }

    protected void handleAcceptEvent(ManageAppointmentForm.AcceptEvent event) {
        appointmentService.findByAppointment(event.getAppointment()).ifPresent(appointment -> {
            Notification.show("Appointment Accepted");
            appointment.setStatus(AppointmentStatus.ACCEPTED);
            appointment.setStatusChangeTime(LocalDateTime.now());
            appointmentService.saveAppointment(appointment);
            handleManageAppointmentDialogClose(null);
        });
    }

    protected void handleRejectEvent(ManageAppointmentForm.RejectEvent event) {
        appointmentService.findByAppointment(event.getAppointment()).ifPresent(appointment -> {
            Notification.show("Appointment Rejected");
            appointment.setStatus(AppointmentStatus.REJECTED);
            appointment.setStatusChangeTime(LocalDateTime.now());
            appointmentService.saveAppointment(appointment);
            handleManageAppointmentDialogClose(null);
        });
    }

    protected void handleRescheduleEvent(ManageAppointmentForm.RescheduleEvent event) {
        Appointment selectedAppointment = event.getAppointment();
        UserProfile<?> selectedUserProfile = appointmentCardGrid.createUserProfile(selectedAppointment);
        schedulingDialog.getForm().setUserProfile(selectedUserProfile);
        schedulingDialog.getForm().setAppointment(selectedAppointment);
        schedulingDialog.open();
    }

    protected void handleCancelEvent(ManageAppointmentForm.CancelEvent event) {
        Appointment appointment = event.getAppointment();

        confirmCancelDialog.addConfirmListener(confirmEvent -> {
            handleAppointmentCancellation(appointment);
        });

        confirmCancelDialog.open();
    }

    private void handleAppointmentCancellation(Appointment appointment) {
        appointmentService.findByAppointment(appointment).ifPresent(foundAppointment -> {
            Notification.show("Appointment Canceled");
            foundAppointment.setStatus(AppointmentStatus.CANCELED);
            foundAppointment.setStatusChangeTime(LocalDateTime.now());
            appointmentService.saveAppointment(foundAppointment);

            confirmCancelDialog.close();
            handleManageAppointmentDialogClose(null);
        });
    }

    @Override
    protected void handleSchedulingDialogClose(SchedulingForm.CancelEvent event) {
        handleManageAppointmentDialogClose(null);
        schedulingDialog.getForm().clearProfileAndFields();
        schedulingDialog.close();
    }

    protected abstract void configureManageAppointmentFormEvents();

    protected abstract void configureAvailableButtons(Appointment appointment);
}
