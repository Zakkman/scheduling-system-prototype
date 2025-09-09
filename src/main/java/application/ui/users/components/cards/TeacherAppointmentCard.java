package application.ui.users.components.cards;


import application.backend.appointment.models.Appointment;
import application.backend.users.models.User;
import application.ui.users.components.cards.profiles.UserProfile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.Getter;

@Getter
public class TeacherAppointmentCard extends AppointmentCard {

    private final Button confirmButton;
    private final Button rejectButton;
    private final Button rescheduleButton;

    public TeacherAppointmentCard(Appointment appointment,
                                  User currentUser,
                                  UserProfile<?> userProfile) {
        super(appointment, currentUser, userProfile);

        this.confirmButton = new Button("Confirm");
        this.rejectButton = new Button("Reject");
        this.rescheduleButton = new Button("Reschedule");

        createFooter();
    }

    @Override
    Component createFooterContent() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(confirmButton, rejectButton, rescheduleButton);

        return buttonLayout;
    }

}
