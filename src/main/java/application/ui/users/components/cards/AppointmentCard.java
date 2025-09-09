package application.ui.users.components.cards;

import application.backend.appointment.models.Appointment;
import application.backend.common.enums.Role;
import application.backend.users.models.User;
import application.ui.users.components.cards.profiles.UserProfile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
        add(createBody(currentUser, userProfile));
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
        subtitleLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        subtitleLayout.setWidth("auto");

        String date = Optional.ofNullable(appointment)
            .map(Appointment::getDate)
            .map(localDate -> localDate.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.US) +
                " " + localDate.getDayOfMonth() +
                ", " + localDate.getYear())
            .orElse("Error: Date not specified");

        String startTime = Optional.ofNullable(appointment)
            .map(Appointment::getStartTime)
            .map(localTime -> localTime.format(DateTimeFormatter.ofPattern("h:mm a")))
            .orElse("---");

        String endTime = Optional.ofNullable(appointment)
            .map(Appointment::getEndTime)
            .map(localTime -> localTime.format(DateTimeFormatter.ofPattern("h:mm a")))
            .orElse("---");

        Icon calendarIcon = new Icon(VaadinIcon.CALENDAR);
        Icon clockIcon = new Icon(VaadinIcon.CLOCK);

        calendarIcon.setSize("0.75em");
        clockIcon.setSize("0.75em");

        HorizontalLayout dateLayout = new HorizontalLayout(calendarIcon, new Span("Date: " + date));
        dateLayout.addClassNames("appointment-subtitle-item", "highlighted-subtitle", "appointment-card-date");
        dateLayout.setSpacing("3px");
        dateLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout timeLayout = new HorizontalLayout(clockIcon, new Span("Time: " + startTime + "  to  " + endTime));
        timeLayout.addClassNames("appointment-subtitle-item", "highlighted-subtitle");
        timeLayout.setSpacing("3px");
        timeLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        subtitleLayout.add(dateLayout, timeLayout);
        return subtitleLayout;
    }

    private void setBackgroundByStatus() {
        String backgroundColor = switch (appointment.getStatus()) {
            case PENDING -> "var(--lumo-primary-color-10pct)";
            case ACCEPTED -> "var(--lumo-success-color-10pct)";
            case CANCELED -> "var(--lumo-contrast-50pct)";
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
                badge.add(VaadinIcon.HAND.create());
                badge.getElement().getThemeList().add("badge small");
            }
            case ACCEPTED -> {
                badge.add(VaadinIcon.CHECK_CIRCLE_O.create());
                badge.getElement().getThemeList().add("badge success small");
            }
            case REJECTED -> {
                badge.add(VaadinIcon.EXCLAMATION_CIRCLE_O.create());
                badge.getElement().getThemeList().add("badge error small");
            }
            case CANCELED -> {
                badge.add(VaadinIcon.CLOSE_CIRCLE.create());
                badge.getElement().getThemeList().add("badge contrast small");
            }
            case UNRESOLVED -> {
                badge.add(VaadinIcon.HAND.create());
                badge.getElement().getThemeList().add("badge error primary small");
            }
        }

        return badge;
    }

    //TODO: FIX THIS BRUH, FIX THE APPOINTED BY, PLACE AND DESCRIPTION

    private Component createBody(User currentUser, UserProfile<?> userProfile) {
        VerticalLayout bodyLayout = new VerticalLayout();
        bodyLayout.setPadding(false);
        bodyLayout.addClassName("appointment-card-body");

        User appointer = appointment.getAppointer();
        Div appointedByDiv = new Div();
        appointedByDiv.addClassName("appointment-card-appointed-by");

        appointedByDiv.add("Appointed by: ");

        if (appointer.equals(currentUser)) {
            Span you = new Span("You");
            you.addClassName("appointer-is-you-indicator");
            appointedByDiv.add(you);
        } else {
            appointedByDiv.add(appointer.getFirstName() + " " + appointer.getLastName());
        }

        Icon placeIcon = VaadinIcon.LOCATION_ARROW_CIRCLE_O.create();
        placeIcon.setSize("1em");

        Span placeLabel = new Span(" Place: ");
        placeLabel.addClassName("appointment-details-place-label");

        Span placeText = new Span(Optional.ofNullable(appointment)
            .map(Appointment::getPlace)
            .orElse("No place provided."));

        Div placeContent = new Div(placeIcon, placeLabel, placeText);
        placeContent.addClassName("appointment-details-place-content");

        FlexLayout placeLayout = new FlexLayout(placeContent);
        placeLayout.addClassName("appointment-details-place-layout");
        placeLayout.getStyle().set("gap", "5px");
        placeLayout.setFlexGrow(1);
        placeLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        placeLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        Icon descriptionIcon = VaadinIcon.CLIPBOARD_TEXT.create();
        descriptionIcon.setSize("1em");

        Span descriptionLabel = new Span(" Description: ");
        descriptionLabel.addClassName("appointment-details-description-label");

        Span descriptionText = new Span(Optional.ofNullable(appointment)
            .map(Appointment::getDescription)
            .orElse("No description provided."));

        Div descriptionContent = new Div(descriptionIcon, descriptionLabel, descriptionText);
        descriptionContent.addClassName("appointment-details-description-content");

        FlexLayout descriptionLayout = new FlexLayout(descriptionContent);
        descriptionLayout.addClassName("appointment-details-description-layout");
        descriptionLayout.getStyle().set("gap", "5px");
        descriptionLayout.setFlexGrow(1);
        descriptionLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        descriptionLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        VerticalLayout detailsContent = new VerticalLayout(placeLayout, descriptionLayout);
        detailsContent.setFlexGrow(1);
        detailsContent.setPadding(false);

        if (userProfile != null) {
            bodyLayout.add(userProfile);
            userProfile.setWidthFull();
        } else {
            bodyLayout.add(new Div("User profile not available."));
        }

        Details details = new Details("Details", detailsContent);
        details.addClassName("appointment-card-details");
        details.addThemeVariants(DetailsVariant.FILLED);
        details.addThemeVariants(DetailsVariant.REVERSE);
        details.setWidthFull();

        bodyLayout.add(appointedByDiv, details);

        return bodyLayout;
    }
}