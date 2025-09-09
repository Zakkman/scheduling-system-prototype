package application.ui.users.components.cards.profiles;

import application.backend.users.models.SpecificUser;
import application.backend.users.models.User;
import application.ui.util.ColorGenerator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.popover.Popover;
import com.vaadin.flow.dom.Element;
import lombok.Getter;

public abstract class UserProfile<T extends SpecificUser<?>> extends Card {

    @Getter
    protected final T specificUser;
    @Getter
    private final User user;

    public UserProfile(T specificUser, User user) {
        this.specificUser = specificUser;
        this.user = user;
        addClassName("user-profile");

        setTitle(createName());
        setSubtitle(createSchoolUnit());
        setHeaderPrefix(createAvatar());
        setHeaderSuffix(createDetails());

        String schoolUnitName = getSchoolUnitName();
        String backgroundColor = ColorGenerator.generateColorFromName(schoolUnitName);
        getElement().getStyle().set("background-color", backgroundColor);
    }

    private Component createName() {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        Div nameDiv = new Div(firstName + " " + lastName);
        nameDiv.addClassName("profile-name-wrap");

        return nameDiv;
    }

    private Component createSchoolUnit() {
        String schoolUnitName = getSchoolUnitName();
        Span schoolUnitSpan = new Span(schoolUnitName);
        schoolUnitSpan.addClassName("teacher-profile-subtitle");
        schoolUnitSpan.addClassName("truncate");

        return schoolUnitSpan;
    }

    private Component createAvatar() {
        return new Avatar(user.getFirstName());
    }

    private Component createDetails() {
        Icon detailsIcon = VaadinIcon.INFO_CIRCLE_O.create();
        detailsIcon.getStyle().set("cursor", "pointer");

        Popover popover = new Popover();
        popover.setTarget(detailsIcon);
        popover.add(getDetailsContent());

        Element element = detailsIcon.getElement();
        element.addEventListener("click", event -> {
        }).addEventData("event.stopPropagation()");

        return new Div(detailsIcon, popover);
    }

    abstract String getSchoolUnitName();

    abstract Component getDetailsContent();
}
