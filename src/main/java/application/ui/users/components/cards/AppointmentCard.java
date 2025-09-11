package application.ui.users.components.cards;

import application.backend.appointment.models.Appointment;
import application.backend.common.enums.Role;
import application.backend.users.models.User;
import application.ui.users.components.cards.profiles.UserProfile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;

@Getter
public class AppointmentCard extends Card {

    private final Appointment appointment;
    private final UserProfile<?> userProfile;

    public AppointmentCard(Appointment appointment, User currentUser, UserProfile<?> userProfile) {
        this.appointment = appointment;
        this.userProfile = userProfile;
        addClassName("appointment-card");

        setBackgroundByStatus();

        setTitle(createTitle(currentUser));
        setSubtitle(createSubtitle());
        setHeaderSuffix(createBadge());
        add(createBody(userProfile));
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
        FlexLayout subtitleLayout = new FlexLayout();
        subtitleLayout.addClassName("appointment-card-subtitle");
        subtitleLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);

        String date = Optional.ofNullable(appointment)
            .map(Appointment::getDate)
            .map(localDate -> localDate.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.US) +
                " " + localDate.getDayOfMonth() +
                ", " + localDate.getYear())
            .orElse("Error: Date not specified");

        String time = Optional.ofNullable(appointment)
            .map(Appointment::getStartTime)
            .map(localTime -> localTime.format(DateTimeFormatter.ofPattern("hh:mm a")))
            .orElse("Error: Time not specified");

        Span dateSpan = new Span("Date: " + date);
        Span timeSpan = new Span("Time: " + time);

        dateSpan.addClassNames("appointment-subtitle-item", "highlighted-subtitle");
        timeSpan.addClassNames("appointment-subtitle-item", "highlighted-subtitle");

        dateSpan.addClassName("appointment-card-date");

        subtitleLayout.add(dateSpan, timeSpan);
        return subtitleLayout;
    }

    private void setBackgroundByStatus() {
        String backgroundColor = switch (appointment.getStatus()) {
            case PENDING -> "var(--lumo-primary-color-10pct)";
            case CONFIRMED -> "var(--lumo-success-color-10pct)";
            case RESOLVED -> "var(--lumo-success-color-15pct)";
            case REJECTED -> "var(--lumo-error-color-10pct)";
            case UNRESOLVED -> "var(--lumo-error-color-15pct)";
            default -> "var(--lumo-contrast-5pct)";
        };
        getStyle().set("background-color", backgroundColor);
    }

    private Component createBadge() {
        Span badge = new Span();

        switch (appointment.getStatus()) {
            case PENDING -> {
                badge.add(VaadinIcon.CLOCK.create());
                badge.getElement().getThemeList().add("badge small");
            }
            case CONFIRMED -> {
                badge.add(VaadinIcon.CHECK_CIRCLE_O.create());
                badge.getElement().getThemeList().add("badge success small");
            }
            case REJECTED -> {
                badge.add(VaadinIcon.EXCLAMATION_CIRCLE_O.create());
                badge.getElement().getThemeList().add("badge error small");
            }
            case RESOLVED -> {
                badge.add(VaadinIcon.CHECK_CIRCLE.create());
                badge.getElement().getThemeList().add("badge success primary small");
            }
            case UNRESOLVED -> {
                badge.add(VaadinIcon.HAND.create());
                badge.getElement().getThemeList().add("badge error primary small");
            }
        }

        return badge;
    }

//    private String capitalizeFirstLetter(String text) {
//        if (text == null || text.isEmpty()) {
//            return text;
//        }
//        return text.substring(0, 1).toUpperCase(Locale.ROOT) + text.substring(1).toLowerCase(Locale.ROOT);
//    }

    //TODO: fix those body details stuff

    private Component createBody(UserProfile<?> userProfile) {
        VerticalLayout bodyLayout = new VerticalLayout();
        bodyLayout.setPadding(false);
        bodyLayout.addClassName("appointment-card-body");

        User appointer = appointment.getAppointer();
        String appointerName = appointer.getFirstName() + " " + appointer.getLastName();
        Div appointedByDiv = new Div("Appointed by: "  + appointerName);
        appointedByDiv.addClassName("appointment-card-appointed-by");

        String place = Optional.ofNullable(appointment)
            .map(Appointment::getPlace)
            .orElse("No place provided.");
        Div placeDiv = new Div("Place: " + place);
        placeDiv.addClassName("appointment-card-place");

        String description = Optional.ofNullable(appointment)
            .map(Appointment::getDescription)
            .orElse("No description provided.");
        Div descriptionDiv = new Div("Description: " + description);
        descriptionDiv.addClassName("appointment-card-description");

        if (userProfile != null) {
            bodyLayout.add(userProfile);
            userProfile.setWidthFull();
        } else {
            bodyLayout.add(new Div("User profile not available."));
        }

        bodyLayout.add(appointedByDiv, descriptionDiv, placeDiv);

        return bodyLayout;
    }
}