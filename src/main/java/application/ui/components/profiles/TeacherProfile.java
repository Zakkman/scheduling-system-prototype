package application.ui.components.profiles;

import application.backend.users.models.Teacher;
import application.backend.users.models.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.popover.Popover;
import com.vaadin.flow.dom.Element;

public class TeacherProfile extends UserProfile<Teacher> {

    public TeacherProfile(Teacher teacher, User user) {
        super(teacher, user);
        addClassName("teacher-profile");
    }

    @Override
    Component getName() {
        String firstName = profileSpecificUser.getUser().getFirstName();
        String lastName = profileSpecificUser.getUser().getLastName();
        Div nameDiv = new Div(firstName + " " + lastName);
        nameDiv.addClassName("profile-name-wrap");

        return nameDiv;
    }

    @Override
    Component getSchoolUnit() {
        String departmentName = profileSpecificUser.getDepartment().getName();
        Span departmentSpan = new Span(departmentName + " Department");
        departmentSpan.addClassName("teacher-profile-subtitle");
        departmentSpan.addClassName("truncate");

        return departmentSpan;
    }

    @Override
    Component getAvatar() {
        return new Avatar(profileSpecificUser.getUser().getFirstName());
    }

    @Override
    Component getDetails() {
        Icon detailsIcon = VaadinIcon.ELLIPSIS_DOTS_H.create();
        detailsIcon.getStyle().set("cursor", "pointer");

        Popover popover = new Popover();
        popover.setTarget(detailsIcon);
        popover.add(createDetailsContent());

        Element element = detailsIcon.getElement();
        element.addEventListener("click", event -> {
        }).addEventData("event.stopPropagation()");

        return new Div(detailsIcon, popover);
    }

    private Component createDetailsContent() {

        VerticalLayout content = new VerticalLayout();
        content.setSpacing(false);
        content.setPadding(false);

        Span subjectsHeader = new Span("Subjects Handled:");
        subjectsHeader.getStyle().set("font-weight", "bold");
        content.add(subjectsHeader);
        profileSpecificUser.getSubjectsHandled().forEach(subject -> content.add(new ListItem(subject.getName())));

        Span sectionsHeader = new Span("Sections Handled:");
        sectionsHeader.getStyle().set("font-weight", "bold");
        content.add(sectionsHeader);
        profileSpecificUser.getSectionsHandled().forEach(section -> content.add(new ListItem(section.getName())));

        return content;
    }
}