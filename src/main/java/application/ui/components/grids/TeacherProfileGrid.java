package application.ui.components.grids;

import application.backend.users.models.Teacher;
import application.backend.users.services.TeacherService;
import application.ui.components.profiles.TeacherProfile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
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
        this.teacherService = teacherService;

        configureGrid();
        configureSearchField(searchByName, "By name");
        configureSearchField(searchBySchoolDeta, "By department/section/subject");

        add(createToolBar(), teacherGrid);
        setFlexGrow(1, teacherGrid);

        updateList();
    }

    private void configureGrid() {
        teacherGrid.setColumns();
        teacherGrid.removeAllHeaderRows();
        teacherGrid.addComponentColumn(TeacherProfile::new)
            .setFlexGrow(1)
            .setAutoWidth(true);
    }

    private Component createToolBar() {
        VerticalLayout toolBar = new VerticalLayout(searchByName, searchBySchoolDeta);
        toolBar.setPadding(false);
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
