package application.ui.components.forms.scheduling;

import application.ui.components.profiles.UserProfile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.timepicker.TimePicker;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Locale;

public class SchedulingForm extends FormLayout {

    //TODO: make this actually connect to making appointments

    private final VerticalLayout userProfileDisplay;

    DatePicker date = new DatePicker("Select Date");
    TimePicker startTime = new TimePicker("Start Time");
    TimePicker endTime = new TimePicker("End Time");
    TextArea description = new TextArea("Description");

    Button appointButton = new Button("Appoint");
    Button clearButton = new Button("Clear");
    @Getter
    Button cancelButton = new Button("Cancel");

    public SchedulingForm() {
        this.userProfileDisplay = new VerticalLayout();

        configureDatePicker();
        configureTimePickers();

        clearButton.addClickListener(click -> clearFields());

        add(new H3("Schedule an Appointment"));
        add(userProfileDisplay);
        add(
            date,
            startTime,
            endTime,
            description,
            createButtonLayout()
        );
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

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout(appointButton, clearButton, cancelButton);
        appointButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        clearButton.addClickListener(e -> clearFields());

        return buttonLayout;
    }

    public void setUserProfile(UserProfile userProfile) {
        userProfileDisplay.removeAll();

        VerticalLayout profileComponent = (VerticalLayout) userProfile;
        userProfileDisplay.add(profileComponent);

        clearFields();
    }

    public void clearFields() {
        date.clear();
        startTime.clear();
        endTime.clear();
        description.clear();
    }
}
