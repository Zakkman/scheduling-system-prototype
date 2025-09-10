package application.ui.users.components.grids;

import application.backend.appointment.models.Appointment;
import application.backend.common.enums.Role;
import application.backend.users.models.Student;
import application.backend.users.models.Teacher;
import application.backend.users.models.User;
import application.backend.users.services.StudentService;
import application.backend.users.services.TeacherService;
import application.backend.appointment.services.AppointmentService;
import application.ui.users.components.cards.AppointmentCard;
import application.ui.users.components.cards.profiles.StudentProfile;
import application.ui.users.components.cards.profiles.TeacherProfile;
import application.ui.users.components.cards.profiles.UserProfile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppointmentCardGrid extends VerticalLayout {

    @Getter
    private final Grid<Appointment> grid;

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final AppointmentService appointmentService;

    private User currentUser;

    private final Select<String> dateSortFilter = new Select<>();
    private final Select<String> roleFilter = new Select<>();

    public AppointmentCardGrid(TeacherService teacherService,
                               StudentService studentService,
                               AppointmentService appointmentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.appointmentService = appointmentService;

        this.grid = new Grid<>(Appointment.class, false);

        addClassName("appointment-card-grid");

        configureFilters();
        configureGrid();

        add(createToolBar(), grid);
        setFlexGrow(1, grid);
    }

    private void configureGrid() {
        grid.addClassName("no-padding-cell");
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setColumns();
        grid.addComponentColumn(this::createAppointmentCardComponent)
            .setHeader(new H3("Appointments:"))
            .setFlexGrow(1)
            .setAutoWidth(true);
    }

    private void configureFilters() {
        dateSortFilter.setLabel("Sort by Date");
        dateSortFilter.setItems("Ascending", "Descending", "Any");
        dateSortFilter.setValue("Ascending");
        dateSortFilter.addValueChangeListener(event -> updateGrid());

        roleFilter.setLabel("Filter by Role");
        roleFilter.setItems("Any", "Teacher", "Student");
        roleFilter.setValue("Any");
        roleFilter.addValueChangeListener(event -> updateGrid());
    }

    private Component createToolBar() {
        FlexLayout toolBar = new FlexLayout(dateSortFilter, roleFilter);
        toolBar.addClassNames("appointment-grid-toolbar");
        toolBar.setWidthFull();
        toolBar.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        return toolBar;
    }

    public void setAuthenticatedUser(User user) {
        this.currentUser = user;
        updateGrid();
    }

    private void updateGrid() {
        if (currentUser == null) {
            return;
        }

        List<Appointment> appointments = appointmentService.getAppointmentsForUser(currentUser);

        String dateSort = dateSortFilter.getValue();
        String selectedRole = roleFilter.getValue();

        List<Appointment> filteredAndSortedAppointments = appointments.stream()
            .filter(appointment -> {
                if ("Any".equals(selectedRole)) {
                    return true;
                }
                User otherUser = appointment.getAppointer().equals(currentUser) ? appointment.getAppointee() : appointment.getAppointer();
                boolean matchesRole = false;
                if ("Teacher".equals(selectedRole)) {
                    matchesRole = otherUser.getRole() == Role.TEACHER;
                } else if ("Student".equals(selectedRole)) {
                    matchesRole = otherUser.getRole() == Role.STUDENT;
                }
                return matchesRole;
            })
            .sorted((a1, a2) -> {
                if ("Ascending".equals(dateSort)) {
                    return a1.getDate().atTime(a1.getStartTime()).compareTo(a2.getDate().atTime(a2.getStartTime()));
                } else if ("Descending".equals(dateSort)) {
                    return a2.getDate().atTime(a2.getStartTime()).compareTo(a1.getDate().atTime(a1.getStartTime()));
                } else {
                    return 0;
                }
            })
            .collect(Collectors.toList());

        grid.setItems(filteredAndSortedAppointments);
    }

    private AppointmentCard createAppointmentCardComponent(Appointment appointment) {
        UserProfile<?> userProfile = createUserProfile(appointment);

        if (currentUser == null || userProfile == null) {
            return null;
        }

        // Changed to use the single AppointmentCard class
        return new AppointmentCard(appointment, currentUser, userProfile);
    }

    private UserProfile<?> createUserProfile(Appointment appointment) {
        Optional<User> otherUserOptional = Optional.ofNullable(currentUser)
            .flatMap(user -> {
                if (user.equals(appointment.getAppointer())) {
                    return Optional.ofNullable(appointment.getAppointee());
                } else {
                    return Optional.ofNullable(appointment.getAppointee());
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