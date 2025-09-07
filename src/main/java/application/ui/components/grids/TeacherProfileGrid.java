package application.ui.components.grids;

import application.backend.users.models.Teacher;
import application.backend.users.models.User;
import application.backend.users.services.TeacherService;
import application.ui.components.profiles.TeacherProfile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

public class TeacherProfileGrid extends VerticalLayout {

    //TODO: make this better maybe

    private final TeacherService teacherService;

    @Getter
    Grid<Teacher> teacherGrid = new Grid<>(Teacher.class);
    TextField searchByName = new TextField();
    TextField searchBySchoolDeta = new TextField();

    public TeacherProfileGrid(TeacherService teacherService) {
        addClassName("teacher-profile-grid");
        this.teacherService = teacherService;

        configureGrid();
        configureSearchField(searchByName, "By name");
        configureSearchField(searchBySchoolDeta, "By department/section/subject");

        add(createToolBar(), teacherGrid);
        setFlexGrow(1, teacherGrid);

        updateList();
    }

    //TODO: make sure this works

    private void configureGrid() {
        teacherGrid.setColumns();
        teacherGrid.removeAllHeaderRows();
        teacherGrid.addComponentColumn(teacher -> {
                User user = teacher.getUser();
                return new TeacherProfile(teacher, user);
            })
            .setFlexGrow(1)
            .setAutoWidth(true);
    }

    private Component createToolBar() {
        FlexLayout toolBar = new FlexLayout(searchByName, searchBySchoolDeta);
        toolBar.addClassNames("teacher-profile-grid-toolbar");
        toolBar.setWidthFull();
        toolBar.setFlexWrap(FlexLayout.FlexWrap.WRAP);

        return toolBar;
    }

    private void updateList() {
        teacherGrid.setItems(teacherService.findAllTeachers(
            searchByName.getValue(), searchBySchoolDeta.getValue())
        );
    }

    private void configureSearchField(TextField searchField, String label) {
        searchField.setWidthFull();
        searchField.setMaxWidth("300px");
        searchField.setLabel(label);
        searchField.setPlaceholder("Search...");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(com.vaadin.flow.data.value.ValueChangeMode.LAZY);
        searchField.addValueChangeListener(change -> updateList());
    }
}
