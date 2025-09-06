package application.ui.teacher;

import application.backend.users.models.Teacher;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TeacherProfile extends VerticalLayout {
    public TeacherProfile(Teacher teacher) {
        // Set styling directly on the VerticalLayout to fill the whole cell
        getStyle().set("border", "1px solid #ccc")
            .set("border-radius", "8px")
            .set("padding", "10px")
            .set("margin", "0"); // Set margin to 0 to remove extra space

        // Set the component to take up the full available width of the cell
        setWidthFull();

        // Display name and department
        H3 name = new H3(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
        Span department = new Span("Department: " + teacher.getDepartment().getName());

        // Create a Details component for the collapsible subjects and sections
        Details details = new Details();
        details.setSummaryText("Show Subjects & Sections");

        // Build the content for the collapsible part
        Div content = new Div();
        content.add(new Span("Subjects Handled:"));
        // Stream and join the subject names
        teacher.getSubjectsHandled().forEach(subject -> content.add(new Div(subject.getName())));

        content.add(new Div("Sections Handled:"));
        // Stream and join the section names
        teacher.getSectionsHandled().forEach(section -> content.add(new Div(section.getName())));

        details.setContent(content);

        // Add components directly to the layout
        add(name, department, details);
    }
}