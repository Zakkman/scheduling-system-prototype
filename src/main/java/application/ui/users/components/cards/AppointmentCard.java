package application.ui.users.components.cards;

import application.backend.appointment.models.Appointment;
import application.backend.common.enums.Role;
import application.backend.users.models.User;
import application.ui.users.components.cards.profiles.UserProfile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Optional;

public abstract class AppointmentCard extends Card {

    protected final Appointment appointment;

    public AppointmentCard(Appointment appointment, User currentUser, UserProfile<?> userProfile) {
        this.appointment = appointment;
        addClassName("appointment-card");

        setTitle(createTitle(currentUser));
        setSubtitle(createSubtitle());
        add(createProfile(userProfile));
    }

    private Component createTitle(User currentUser) {
        Optional<Role> otherRole = Optional.ofNullable(appointment)
            .flatMap(app -> {
                if (currentUser.equals(app.getAppointer())) {
                    return Optional.ofNullable(app.getAppointee()).map(User::getRole);
                } else {
                    return Optional.ofNullable(app.getAppointer()).map(User::getRole);
                }
            });

        Div roleNameDiv = new Div(otherRole.map(Enum::name).orElse("Unknown User"));
        return new Div("Meeting with: " + roleNameDiv.getText());
    }

    private Component createSubtitle() {
        HorizontalLayout subtitleLayout = new HorizontalLayout();
        subtitleLayout.addClassName("appointment-card-subtitle");

        String date = Optional.ofNullable(appointment)
            .map(Appointment::getDate)
            .map(Object::toString)
            .orElse("Error: Date not specified");
        String time = Optional.ofNullable(appointment)
            .map(Appointment::getStartTime)
            .map(Object::toString)
            .orElse("Error: Time not specified");

        Span dateSpan = new Span("Date: " + date);
        Span timeSpan = new Span("Time: " + time);

        dateSpan.addClassNames("appointment-subtitle-item", "highlighted-subtitle");
        timeSpan.addClassNames("appointment-subtitle-item", "highlighted-subtitle");

        subtitleLayout.add(dateSpan, timeSpan);
        return subtitleLayout;
    }

    private Component createProfile(UserProfile<?> userProfile) {
        VerticalLayout bodyLayout = new VerticalLayout();
        bodyLayout.setPadding(false);
        bodyLayout.addClassName("appointment-card-body");

        String place = Optional.ofNullable(appointment)
            .map(Appointment::getPlace)
            .orElse("No place provided.");
        Div placeDiv = new Div("Place: " + place);

        String description = Optional.ofNullable(appointment)
            .map(Appointment::getDescription)
            .orElse("No description provided.");
        Div descriptionDiv = new Div("Description: " + description);

        if (userProfile != null) {
            bodyLayout.add(userProfile);
        } else {
            bodyLayout.add(new Div("User profile not available."));
        }

        userProfile.setWidthFull();

        bodyLayout.add(descriptionDiv, placeDiv);
        return bodyLayout;
    }

    protected void createFooter() {
        Component footer = createFooterContent();
        footer.addClassName("appointment-card-footer");

        addToFooter(footer);
    }

    abstract Component createFooterContent();
}