package application.ui.users.components.cards.profiles;

import application.backend.users.models.Teacher;
import application.backend.users.models.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TeacherProfile extends UserProfile<Teacher> {

    public TeacherProfile(Teacher teacher, User user) {
        super(teacher, user);
        addClassName("teacher-profile");
    }

    @Override
    String getSchoolUnitName() {
        return specificUser.getDepartment().getName() + " Department";
    }

    @Override
    Component getDetailsContent() {
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(false);
        content.setPadding(false);

        Span subjectsHeader = new Span("Subjects Handled:");
        subjectsHeader.getStyle().set("font-weight", "bold");
        content.add(subjectsHeader);
        specificUser.getSubjectsHandled().forEach(subject -> content.add(new ListItem(subject.getName())));

        Span spacer = new Span(" ");
        content.add(spacer);

        Span sectionsHeader = new Span("Sections Handled:");
        sectionsHeader.getStyle().set("font-weight", "bold");
        content.add(sectionsHeader);
        specificUser.getSectionsHandled().forEach(section -> content.add(new ListItem(section.getName())));

        return content;
    }
}