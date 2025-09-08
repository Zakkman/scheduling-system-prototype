package application.ui.users.components.grids;

import application.backend.appointment.models.Appointment;
import application.backend.users.models.User;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.ui.users.components.cards.AppointmentCard;
import application.ui.users.components.cards.profiles.StudentProfile;
import application.ui.users.components.cards.profiles.TeacherProfile;
import application.ui.users.components.cards.profiles.UserProfile;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class AppointmentCardGrid extends VerticalLayout {

    private final Grid<Appointment> grid;

    private final TeacherService teacherService;
    private final StudentService studentService;
    private User currentUser;

    public AppointmentCardGrid(TeacherService teacherService,
                               StudentService studentService,
                               User currentUser) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.currentUser = currentUser;

        this.grid = new Grid<>(Appointment.class, false); // Initialize the grid
        configureGrid();

        addClassName("appointment-card-grid");
        setSizeFull();
        setSpacing(false);
        setPadding(false);

        add(grid); // Add the grid to the layout
        setFlexGrow(1, grid);
    }

    private void configureGrid() {
        grid.setColumns(); // Clear any default columns
        grid.addComponentColumn(this::createAppointmentCardComponent) // Use a component column
            .setFlexGrow(1)
            .setAutoWidth(true);
    }

    private AppointmentCard createAppointmentCardComponent(Appointment appointment) {
        try {
            UserProfile<?> userProfile = createUserProfile(appointment);
            return new AppointmentCard(appointment, currentUser, userProfile);
        } catch (Exception e) {
            System.err.println("Failed to create AppointmentCard for Appointment ID: " + appointment.getId());
            e.printStackTrace();
            return new AppointmentCard(appointment, currentUser, null); // Return a placeholder on failure
        }
    }

    public void setAuthenticatedUser(User user) {
        this.currentUser = user;
    }

    public void addAppointmentCards(List<Appointment> appointments) {
        grid.setItems(appointments); // Set the grid's items directly
    }

    private UserProfile<?> createUserProfile(Appointment appointment) {
        User otherUser;

        if (appointment.getAppointer() == null || appointment.getAppointee() == null) {
            return null;
        }

        if (currentUser.equals(appointment.getAppointer())) {
            otherUser = appointment.getAppointee();
        } else {
            otherUser = appointment.getAppointer();
        }

        if (otherUser == null) {
            return null;
        }

        switch (otherUser.getRole()) {
            case TEACHER:
                return teacherService.findByUser(otherUser)
                    .map(teacher -> (UserProfile) new TeacherProfile(teacher, otherUser))
                    .orElse(null);
            case STUDENT:
                return studentService.findByUser(otherUser)
                    .map(student -> (UserProfile) new StudentProfile(student, otherUser))
                    .orElse(null);
            default:
                return null;
        }
    }
}