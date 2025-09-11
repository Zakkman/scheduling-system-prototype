package application.ui.users.components.cards;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Optional;

public class AppointmentCardContainer extends VerticalLayout {

    public AppointmentCardContainer() {
        setPadding(false);
    }

    public void setAppointmentCard(AppointmentCard appointmentCard) {
        removeAll();
        add(appointmentCard);
    }

    public Optional<? extends AppointmentCard> getAppointmentCard() {
        return getChildren()
            .filter(component -> component instanceof AppointmentCard)
            .map(component -> (AppointmentCard) component)
            .findFirst();
    }
}
