package application.ui.components.profiles;

import application.backend.users.models.Teacher;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TeacherProfile extends VerticalLayout implements UserProfile {
    public TeacherProfile(Teacher teacher) {

        //TODO: fix this
        //TODO: make this look better

        getStyle().set("border", "1px solid #ccc")
            .set("border-radius", "8px")
            .set("padding", "10px")
            .set("margin", "0"); // Set margin to 0 to remove extra space

        setWidthFull();

        H3 name = new H3(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
        Span department = new Span("Department: " + teacher.getDepartment().getName());

        Details details = new Details();
        details.setSummaryText("Show Subjects & Sections");

        Div content = new Div();
        content.add(new Span("Subjects Handled:"));

        teacher.getSubjectsHandled().forEach(subject -> content.add(new Div(subject.getName())));

        content.add(new Div("Sections Handled:"));

        teacher.getSectionsHandled().forEach(section -> content.add(new Div(section.getName())));

        details.setContent(content);

        add(name, department, details);
    }
}