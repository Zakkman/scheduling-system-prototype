package application.ui.teacher;

import application.backend.users.models.Teacher;
import application.backend.users.services.TeacherService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class TeacherProfileGrid extends VerticalLayout {

    private final TeacherService teacherService;

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

    private void configureGrid() {
        teacherGrid.setColumns();
        teacherGrid.removeAllHeaderRows();
        teacherGrid.addComponentColumn(TeacherProfile::new)
//            .setHeader(new H3("Teachers"))
            .setFlexGrow(1)
            .setAutoWidth(true);

//        teacherGrid.setAllRowsVisible(true);
    }
}
