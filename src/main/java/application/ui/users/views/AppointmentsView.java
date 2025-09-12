package application.ui.users.views;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.services.AppointmentService;
import application.backend.security.CustomUserDetails;
import application.backend.users.models.User;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.ui.users.components.cards.AppointmentCard;
import application.ui.users.components.cards.profiles.UserProfile;
import application.ui.users.components.grids.AppointmentCardGrid;
import application.ui.users.components.dialogs.ManageAppointmentDialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AppointmentsView extends VerticalLayout implements BeforeEnterObserver {

    protected final AppointmentService appointmentService;


    private final AppointmentCardGrid appointmentCardGrid;
    protected User currentUser;

    protected final ManageAppointmentDialog manageAppointmentDialog;

    public AppointmentsView(AppointmentService appointmentService,
                            TeacherService teacherService,
                            StudentService studentService) {
        this.appointmentService = appointmentService;
        this.appointmentCardGrid = new AppointmentCardGrid(teacherService, studentService, this.appointmentService);
        this.manageAppointmentDialog = new ManageAppointmentDialog();

        add(appointmentCardGrid);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setFlexGrow(1, appointmentCardGrid);

        configureGridSelect();
        configureManageAppointmentDialog();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            this.currentUser = ((CustomUserDetails) principal).getUser();
            this.appointmentCardGrid.setAuthenticatedUser(currentUser);
        } else {
            event.rerouteTo("login");
            Notification.show("Your session has expired. Please log in again.");
        }
    }

    private void configureGridSelect() {
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

    protected void handleDialogClose() {
        appointmentCardGrid.getGrid().asSingleSelect().clear();
        manageAppointmentDialog.getForm().clear();
        manageAppointmentDialog.close();
    }

    protected abstract void configureManageAppointmentDialog();

    protected abstract void configureAvailableButtons(Appointment appointment);
}
