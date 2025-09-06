package application.ui.student;

import application.backend.users.models.Teacher;
import application.backend.users.services.TeacherService;
import application.ui.layouts.UserLayout;
import application.ui.teacher.TeacherProfile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "student", layout = UserLayout.class)
@RolesAllowed("STUDENT")
public class StudentView extends VerticalLayout {

    private final TeacherService teacherService;

    Grid<Teacher> teacherGrid = new Grid<>(Teacher.class);
    TextField filterByName = new TextField();
    TextField filterBySchoolDeta = new TextField();

    public StudentView(TeacherService teacherService) {
        this.teacherService = teacherService;

//        setSizeFull();
//        setPadding(true);

        configureGrid();
        configureSearchBar();

        add(createToolBar(), teacherGrid);
        setFlexGrow(1, teacherGrid);

        updateList();
    }

    private Component createToolBar() {
        return new HorizontalLayout(filterByName, filterBySchoolDeta);
    }

    private void updateList() {
        teacherGrid.setItems(teacherService.findAllTeachers(
            filterByName.getValue(), filterBySchoolDeta.getValue())
        );
    }

    private Component configureSearchBar() {
        filterByName.setPlaceholder("Search by name...");
        filterByName.setClearButtonVisible(true);
        filterByName.setValueChangeMode(com.vaadin.flow.data.value.ValueChangeMode.LAZY);
        filterByName.addValueChangeListener(change -> updateList());

        filterBySchoolDeta.setPlaceholder("Search by school data...");
        filterBySchoolDeta.setClearButtonVisible(true);
        filterBySchoolDeta.setValueChangeMode(com.vaadin.flow.data.value.ValueChangeMode.LAZY);
        filterBySchoolDeta.addValueChangeListener(change -> updateList());

        return filterByName;
    }

    private void configureGrid() {
        teacherGrid.setColumns();
        teacherGrid.addComponentColumn(TeacherProfile::new)
            .setHeader("Teachers")
            .setFlexGrow(1)
            .setAutoWidth(true);

//        teacherGrid.setAllRowsVisible(true);
    }

}
