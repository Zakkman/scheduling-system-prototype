package application.ui.users.components.cards;

import application.backend.appointment.models.Appointment;
import application.backend.users.models.User;
import application.ui.users.components.cards.profiles.StudentProfile;
import application.ui.users.components.cards.profiles.TeacherProfile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.Getter;

@Getter
public class StudentAppointmentCard extends AppointmentCard {

    private final Button cancelButton;

    public StudentAppointmentCard(Appointment appointment,
                                  User currentUser,
                                  TeacherProfile teacherProfile) {
        super(appointment, currentUser, teacherProfile);
        this.cancelButton = new Button("Cancel");

        createFooter();
    }

    @Override
    Component createFooterContent() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(cancelButton);

        return buttonLayout;
    }
}
