package application.ui.users.components.cards.profiles;

import application.backend.users.models.Student;
import application.backend.users.models.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.popover.Popover;
import com.vaadin.flow.dom.Element;

public class StudentProfile extends UserProfile<Student> {

    public StudentProfile(Student student, User user) {
        super(student, user);
    }

    @Override
    String getSchoolUnitName() {
        return specificUser.getSection().getName() + " Section";
    }

    @Override
    Component getDetailsContent() {
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(false);
        content.setPadding(false);

        Span trackHeader = new Span("Track:");
        trackHeader.getStyle().set("font-weight", "bold");
        content.add(trackHeader);

        String trackAbbreviation = specificUser.getTrack().getAbbreviation();
        String trackName = specificUser.getTrack().getName();
        String trackFullName = (trackAbbreviation == null) ?
            trackName : trackName + " (" + trackAbbreviation + ")";

        content.add(trackFullName);

        Span spacer = new Span(" ");
        content.add(spacer);

        Span strandHeader = new Span("Strand:");
        strandHeader.getStyle().set("font-weight", "bold");
        content.add(strandHeader);

        String strandAbbreviation = specificUser.getStrand().getAbbreviation();
        String strandName = specificUser.getStrand().getName();
        String strandFullName = (strandAbbreviation == null) ?
            strandName : strandName + " (" + strandAbbreviation + ")";

        content.add(strandFullName);

        return content;
    }
}
