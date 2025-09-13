package application.ui.users.components.forms;

import application.backend.appointment.models.Appointment;
import application.ui.users.components.cards.AppointmentCard;
import application.ui.users.components.containers.AppointmentCardContainer;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

import java.util.function.Consumer;

public class ManageAppointmentForm extends VerticalLayout {

    private final AppointmentCardContainer selectedAppointmentCardContainer;
    private final VerticalLayout buttonLayout;
    @Getter
    private final Button acceptButton;
    @Getter
    private final Button rejectButton;
    @Getter
    private final Button rescheduleButton;
    @Getter
    private final Button cancelButton;
    @Getter
    private final Button backButton;

    public ManageAppointmentForm() {
        this.selectedAppointmentCardContainer = new AppointmentCardContainer();
        this.buttonLayout = new VerticalLayout();
        this.acceptButton = new Button("Accept");
        this.rejectButton = new Button("Reject");
        this.rescheduleButton = new Button("Reschedule");
        this.cancelButton = new Button("Cancel");
        this.backButton = new Button("Back");

        buttonLayout.setPadding(false);
        buttonLayout.setSpacing(false);
        configureButtons();

        selectedAppointmentCardContainer.addClassName("manage-appointment-form");

        acceptButton.addClassName("accept-button");
        rejectButton.addClassName("reject-button");
        rescheduleButton.addClassName("reschedule-button");
        cancelButton.addClassName("cancel-button");
        backButton.addClassName("back-button");

        add(new H3("Manage Appointment"));
        add(selectedAppointmentCardContainer, buttonLayout);

        for (Button button : new Button[] {
            acceptButton,
            rejectButton,
            rescheduleButton,
            cancelButton,
            backButton
        }) {
            buttonLayout.add(button);
            button.setWidthFull();
            button.setHeight("100px");
        }
    }

    public void setAppointmentCard(AppointmentCard appointmentCard) {
        selectedAppointmentCardContainer.setAppointmentCard(appointmentCard);
        appointmentCard.setWidthFull();
    }

    public void clear() {
        selectedAppointmentCardContainer.removeAll();
    }

    private void configureButtons() {
        configureClickListener(acceptButton, appointment ->
            fireEvent(new AcceptEvent(this, appointment))
        );

        configureClickListener(rejectButton, appointment ->
            fireEvent(new RejectEvent(this, appointment))
        );

        configureClickListener(rescheduleButton, appointment ->
            fireEvent(new RescheduleEvent(this, appointment))
        );

        configureClickListener(cancelButton, appointment ->
            fireEvent(new CancelEvent(this, appointment))
        );

        configureClickListener(backButton, appointment ->
            fireEvent(new BackEvent(this))
        );
    }

    private void configureClickListener(Button button, Consumer<Appointment> eventCreator) {
        button.addClickListener(click -> {
            selectedAppointmentCardContainer.getAppointmentCard()
                .ifPresent(appointmentCard -> {
                    Appointment appointment = appointmentCard.getAppointment();
                    eventCreator.accept(appointment);
                });
        });
    }

    public static class ManageAppointmentFormEvent extends ComponentEvent<ManageAppointmentForm> {
        @Getter
        private final Appointment appointment;

        public ManageAppointmentFormEvent(ManageAppointmentForm source, Appointment appointment) {
            super(source, false);
            this.appointment = appointment;
        }
    }

    public static class AcceptEvent extends ManageAppointmentFormEvent {
        AcceptEvent(ManageAppointmentForm source, Appointment appointment) {
            super(source, appointment);
        }
    }

    public static class RejectEvent extends ManageAppointmentFormEvent {
        RejectEvent(ManageAppointmentForm source, Appointment appointment) {
            super(source, appointment);
        }
    }

    public static class RescheduleEvent extends ManageAppointmentFormEvent {
        RescheduleEvent(ManageAppointmentForm source, Appointment appointment) {
            super(source, appointment);
        }
    }

    public static class CancelEvent extends ManageAppointmentFormEvent {
        CancelEvent(ManageAppointmentForm source, Appointment appointment) {
            super(source, appointment);
        }
    }

    public static class BackEvent extends ManageAppointmentFormEvent {
        BackEvent(ManageAppointmentForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
