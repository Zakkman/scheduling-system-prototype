package application.ui.users.components.cards;


import application.backend.appointment.models.Appointment;
import application.backend.users.models.User;
import application.ui.users.components.cards.profiles.UserProfile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
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

        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        rejectButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        rescheduleButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        createFooter();
    }

    @Override
    Component createFooterContent() {
        FlexLayout buttonLayout = new FlexLayout(confirmButton, rejectButton, rescheduleButton);
        buttonLayout.add(confirmButton, rejectButton, rescheduleButton);
        buttonLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        buttonLayout.getStyle().set("gap", "8px");

        return buttonLayout;
    }

}
