package application.ui.users.components.grids;

import application.backend.appointment.models.Appointment;
import application.backend.users.models.Student;
import application.backend.users.models.Teacher;
import application.backend.users.models.User;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.ui.users.components.cards.AppointmentCard;
import application.ui.users.components.cards.StudentAppointmentCard;
import application.ui.users.components.cards.TeacherAppointmentCard;
import application.ui.users.components.cards.profiles.StudentProfile;
import application.ui.users.components.cards.profiles.TeacherProfile;
import application.ui.users.components.cards.profiles.UserProfile;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;
import java.util.Optional;

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

        this.grid = new Grid<>(Appointment.class, false);

        addClassName("appointment-card-grid");
        setSizeFull();
        setSpacing(false);
        setPadding(false);

        configureGrid();

        add(grid);
        setFlexGrow(1, grid);
    }

    private void configureGrid() {
        grid.setColumns();
        grid.addComponentColumn(this::createAppointmentCardComponent)
            .setFlexGrow(1)
            .setAutoWidth(true);
    }

    private AppointmentCard createAppointmentCardComponent(Appointment appointment) {
        UserProfile<?> userProfile = createUserProfile(appointment);

        if (currentUser == null || userProfile == null) {
            return null;
        }

        return
            switch (currentUser.getRole()) {
                case STUDENT -> new StudentAppointmentCard(appointment, currentUser, (TeacherProfile) userProfile);
                case TEACHER -> new TeacherAppointmentCard(appointment, currentUser, userProfile);
                default -> null;
            };
    }

    public void setAuthenticatedUser(User user) {
        this.currentUser = user;
    }

    public void addAppointmentCards(List<Appointment> appointments) {
        grid.setItems(appointments);
    }

    private UserProfile<?> createUserProfile(Appointment appointment) {
        Optional<User> otherUserOptional = Optional.ofNullable(currentUser)
            .flatMap(user -> {
                if (user.equals(appointment.getAppointer())) {
                    return Optional.ofNullable(appointment.getAppointee());
                } else {
                    return Optional.ofNullable(appointment.getAppointer());
                }
            });

        return otherUserOptional
            .flatMap(otherUser -> switch (otherUser.getRole()) {
                case TEACHER -> teacherService.findByUser(otherUser)
                    .map(teacher -> (UserProfile<Teacher>) new TeacherProfile(teacher, otherUser));
                case STUDENT -> studentService.findByUser(otherUser)
                    .map(student -> (UserProfile<Student>) new StudentProfile(student, otherUser));
                default -> Optional.empty();
            })
            .orElse(null);
    }
}