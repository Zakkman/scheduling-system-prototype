package application.ui.components.forms.scheduling;

import application.backend.appointment.models.Appointment;
import application.backend.appointment.models.AppointmentStatus;
import application.backend.users.models.User;
import application.ui.components.custom.UserProfileContainer;
import application.ui.components.profiles.UserProfile;
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

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

public class SchedulingForm extends VerticalLayout {

    Binder<Appointment> binder = new BeanValidationBinder<>(Appointment.class);

    private final User appointer;
    private final AppointmentStatus DEFAULT_STATUS;

    private final UserProfileContainer selectedUserProfileContainer;

    DatePicker date = new DatePicker("Select Date");
    TimePicker startTime = new TimePicker("Start Time");
    TimePicker endTime = new TimePicker("End Time");
    TextArea description = new TextArea("Description");

    Button appointButton = new Button("Appoint");
    Button clearButton = new Button("Clear");
    @Getter
    Button cancelButton = new Button("Cancel");

    //TODO: maybe refactor or organize this or smth cus this is a mess

    public SchedulingForm(User appointer,
                          AppointmentStatus DEFAULT_STATUS) {
        this.appointer = appointer;
        this.DEFAULT_STATUS = DEFAULT_STATUS;
        this.selectedUserProfileContainer = new UserProfileContainer();

        binder.bindInstanceFields(this);

        H3 title = new H3("Schedule an Appointment");

        add(
            title,
            selectedUserProfileContainer,
            date,
            startTime,
            endTime,
            description,
            appointButton,
            clearButton,
            cancelButton
        );

        for (HasSize component : new HasSize[] {
            title,
            selectedUserProfileContainer,
            date,
            startTime,
            endTime,
            description,
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
    }

    private void configureDatePicker() {
        LocalDate now = LocalDate.now();
        LocalDate max = now.plusMonths(2);

        date.setMin(now);
        date.setMax(max);
    }

    private void configureTimePickers() {
        startTime.setLocale(Locale.US);
        endTime.setLocale(Locale.US);
    }

    private void configureButtons() {
        appointButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        clearButton.addClickListener(e -> clearFields());

        appointButton.addClickListener(click -> {
            try {
                Optional<Appointment> appointmentBean = getAppointment();
                appointmentBean.ifPresent(bean ->
                    fireEvent(new AppointEvent(this, bean))
                );
            } catch(ValidationException e) {
                fireEvent(new AppointEvent(this, e));
            }
        });

    }

    private Optional<Appointment> getAppointment() throws ValidationException {
        Appointment appointment = new Appointment();
        binder.writeBean(appointment);

        Optional<? extends UserProfile<?>> profile = selectedUserProfileContainer.getUserProfile();

        if (profile.isEmpty()) {
            return Optional.empty();
        }

        User appointee = profile.get().getProfileUser();

        appointment.setAppointer(appointer);
        appointment.setAppointee(appointee);
        appointment.setStatus(DEFAULT_STATUS);
        return Optional.of(appointment);
    }

    public void setUserProfile(UserProfile userProfile) {
        clearFields();
        selectedUserProfileContainer.addUserProfile(userProfile);
        userProfile.setWidthFull();
    }

    public void clearFields() {
        selectedUserProfileContainer.clear();
        date.clear();
        startTime.clear();
        endTime.clear();
        description.clear();
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
