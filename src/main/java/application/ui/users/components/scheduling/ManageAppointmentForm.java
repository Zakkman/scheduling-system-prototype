package application.ui.users.components.scheduling;

import application.backend.appointment.models.Appointment;
import application.backend.users.models.User;
import application.ui.users.components.cards.AppointmentCard;
import application.ui.users.components.cards.AppointmentCardContainer;
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

    public ManageAppointmentForm() {
        this.selectedAppointmentCardContainer = new AppointmentCardContainer();
        this.buttonLayout = new VerticalLayout();
        this.acceptButton = new Button("Accept");
        this.rejectButton = new Button("Reject");
        this.rescheduleButton = new Button("Reschedule");
        this.cancelButton = new Button("Cancel");

        for (Button button : new Button[] {
            acceptButton,
            rejectButton,
            rescheduleButton,
            cancelButton
        }) {
            button.setWidthFull();
            button.setHeight("100px");
        }

        buttonLayout.setPadding(false);
        buttonLayout.setSpacing(false);
        configureButtons();

        selectedAppointmentCardContainer.addClassName("manage-appointment-form");

        cancelButton.addClassName("cancel-button");
        acceptButton.addClassName("accept-button");
        rejectButton.addClassName("reject-button");
        rescheduleButton.addClassName("reschedule-button");

        add(new H3("Manage Appointment"));
        add(selectedAppointmentCardContainer, buttonLayout);
    }

    public void setAppointmentCard(AppointmentCard appointmentCard) {
        selectedAppointmentCardContainer.setAppointmentCard(appointmentCard);
        appointmentCard.setWidthFull();
    }

    public void clear() {
        selectedAppointmentCardContainer.removeAll();
        buttonLayout.removeAll();
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

    public void addAcceptButton() {
        buttonLayout.add(acceptButton);
    }

    public void addRejectButton() {
        buttonLayout.add(rejectButton);
    }

    public void addRescheduleButton() {
        buttonLayout.add(rescheduleButton);
    }

    public void addCancelButton() {
        buttonLayout.add(cancelButton);
    }

    public static class ManageAppointFormEvent extends ComponentEvent<ManageAppointmentForm> {
        private final Appointment appointment;

        public ManageAppointFormEvent(ManageAppointmentForm source, Appointment appointment) {
            super(source, false);
            this.appointment = appointment;
        }
    }

    public static class AcceptEvent extends ManageAppointFormEvent {
        AcceptEvent(ManageAppointmentForm source, Appointment appointment) {
            super(source, appointment);
        }
    }

    public static class RejectEvent extends ManageAppointFormEvent {
        RejectEvent(ManageAppointmentForm source, Appointment appointment) {
            super(source, appointment);
        }
    }

    public static class RescheduleEvent extends ManageAppointFormEvent {
        RescheduleEvent(ManageAppointmentForm source, Appointment appointment) {
            super(source, appointment);
        }
    }

    public static class CancelEvent extends ManageAppointFormEvent {
        CancelEvent(ManageAppointmentForm source, Appointment appointment) {
            super(source, appointment);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
