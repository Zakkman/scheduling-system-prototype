package application.ui.users.components.forms;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.models.AppointmentStatus;
import application.backend.common.enums.Role;
import application.backend.users.models.User;
import application.ui.users.components.containers.UserProfileContainer;
import application.ui.users.components.cards.profiles.UserProfile;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Optional;

public class SchedulingForm extends VerticalLayout {

    Binder<Appointment> binder = new BeanValidationBinder<>(Appointment.class);

    private final User currentUser;
    private final UserProfileContainer selectedUserProfileContainer;

    TextArea place = new TextArea("Place");
    TextArea description = new TextArea("Description");
    DatePicker date = new DatePicker("Select Date");
    TimePicker startTime = new TimePicker("Start Time");
    TimePicker endTime = new TimePicker("End Time");

    Button appointButton = new Button("Appoint");
    Button clearButton = new Button("Clear");
    @Getter
    Button cancelButton = new Button("Cancel");

    private Appointment savedAppointment;

    private final VerticalLayout formFieldsLayout;
    private final VerticalLayout selfProfileMessage;

    public SchedulingForm(User currentUser) {
        this.currentUser = currentUser;
        this.selectedUserProfileContainer = new UserProfileContainer();

        binder.bindInstanceFields(this);

        H3 title = new H3("Schedule an Appointment");

        formFieldsLayout = new VerticalLayout(
            place,
            description,
            date,
            startTime,
            endTime,
            appointButton,
            clearButton
        );
        formFieldsLayout.setPadding(false);

        selfProfileMessage = new VerticalLayout(new H3("This is your profile :)"));
        selfProfileMessage.setVisible(false);
        selfProfileMessage.setSizeFull();
        selfProfileMessage.setAlignItems(Alignment.CENTER);
        selfProfileMessage.setJustifyContentMode(JustifyContentMode.CENTER);

        add(title,
            selectedUserProfileContainer,
            selfProfileMessage,
            formFieldsLayout,
            cancelButton
        );

        for (HasSize component : new HasSize[]{
            title,
            selectedUserProfileContainer,
            place,
            description,
            date,
            startTime,
            endTime,
            appointButton,
            clearButton,
            cancelButton
        }) {
            component.setWidthFull();
        }

        configureDatePicker();
        configureTimePickers();
        configureButtons();

        selectedUserProfileContainer.setSpacing(false);
        selectedUserProfileContainer.setPadding(false);

        formFieldsLayout.setSpacing("5px");
    }

    private void configureDatePicker() {
        LocalDate now = LocalDate.now();
        LocalDate nextMonth = now.plusMonths(1);
        LocalDate max = nextMonth.withDayOfMonth(nextMonth.lengthOfMonth());

        date.setMin(now);
        date.setMax(max);

        date.setClearButtonVisible(true);
    }

    private void configureTimePickers() {
        startTime.setStep(Duration.ofMinutes(30));
        startTime.setLocale(Locale.US);
        startTime.setMin(LocalTime.of(7, 0));
        startTime.setMax(LocalTime.of(18, 30));

        endTime.setStep(Duration.ofMinutes(30));
        endTime.setLocale(Locale.US);
        endTime.setPlaceholder("Optional...");
        endTime.setMin(LocalTime.of(7, 0));
        endTime.setMax(LocalTime.of(18, 30));

        startTime.setClearButtonVisible(true);
        endTime.setClearButtonVisible(true);
        endTime.setEnabled(false);

        configureTimePickerFlow();
    }

    private void configureTimePickerFlow() {
        startTime.addValueChangeListener(event -> {
            LocalTime selectedStartTime = event.getValue();
            if (selectedStartTime != null) {
                endTime.setMin(selectedStartTime);
                endTime.setEnabled(true);

                if (endTime.getValue() != null && endTime.getValue().isBefore(selectedStartTime)) {
                    endTime.clear();
                }
            } else {
                endTime.setEnabled(false);
                endTime.clear();
            }
        });
    }

    private void configureButtons() {
        appointButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clearButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        for (Button button : new Button[] {
            appointButton,
            clearButton,
            cancelButton
        }) {
            button.setHeight("var(--lumo-size-l)");
        }

        appointButton.addClickListener(click -> {
            try {
                Optional<Appointment> appointmentBean = getAppointment();
                appointmentBean.ifPresent(bean ->
                    fireEvent(new AppointEvent(this, bean))
                );
            } catch (ValidationException e) {
                fireEvent(new AppointEvent(this, e));
            }
        });

        clearButton.addClickListener(e -> clearFields());

        cancelButton.addClickListener(click ->
            fireEvent(new CancelEvent(this))
        );
    }

    public void setUserProfile(UserProfile userProfile) {
        clearProfileAndFields();
        selectedUserProfileContainer.addUserProfile(userProfile);
        userProfile.setWidthFull();

        if (currentUser.equals(userProfile.getUser())) {
            setFormVisibility(false);
        } else {
            setFormVisibility(true);
        }
    }

    private void setFormVisibility(boolean visible) {
        formFieldsLayout.setVisible(visible);
        selfProfileMessage.setVisible(!visible);
    }

    public void clearProfileAndFields() {
        selectedUserProfileContainer.removeAll();
        clearFields();
        setFormVisibility(true);
    }

    public void clearFields() {
        binder.readBean(null);

        place.clear();
        description.clear();
        date.clear();
        startTime.clear();
        endTime.clear();
    }

    private Optional<Appointment> getAppointment() throws ValidationException {
        Appointment appointment;
        if (savedAppointment != null) {
            appointment = savedAppointment;
        } else {
            appointment = new Appointment();
        }

        binder.writeBean(appointment);

        Optional<? extends UserProfile<?>> profile = selectedUserProfileContainer.getUserProfile();

        if (profile.isEmpty()) {
            return Optional.empty();
        }

        User appointee = profile.get().getUser();

        appointment.setAppointer(currentUser);
        appointment.setAppointee(appointee);

        AppointmentStatus appointmentStatus = getStatusBasedOnRoles(appointee);
        appointment.setStatus(appointmentStatus);
        appointment.setStatusChangeTime(LocalDateTime.now());

        return Optional.of(appointment);
    }

    private AppointmentStatus getStatusBasedOnRoles(User appointee) {
        boolean isStudent = currentUser.getRole().equals(Role.STUDENT);
        boolean isTeacher = currentUser.getRole().equals(Role.TEACHER);

        if (isStudent || isTeacher && appointee.getRole().equals(Role.TEACHER)) {
            return AppointmentStatus.PENDING;
        } else {
            return AppointmentStatus.ACCEPTED;
        }
    }

    public void setAppointment(Appointment appointment) {
        this.savedAppointment = appointment;
        binder.readBean(appointment);
    }

    public static class AppointEvent extends ComponentEvent<SchedulingForm> {

        private final Appointment appointment;
        private final ValidationException validationException;

        protected AppointEvent(SchedulingForm source, Appointment appointment) {
            super(source, false);
            this.appointment = appointment;
            this.validationException = null;
        }

        public AppointEvent(SchedulingForm source, ValidationException validationException) {
            super(source, false);
            this.appointment = null;
            this.validationException = validationException;
        }

        public Appointment getAppointmentOrThrow() throws ValidationException {
            if (validationException != null) {
                throw this.validationException;
            }
            return appointment;
        }
    }

    public static class CancelEvent extends ComponentEvent<SchedulingForm> {
        CancelEvent(SchedulingForm source) {
            super(source, false);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
