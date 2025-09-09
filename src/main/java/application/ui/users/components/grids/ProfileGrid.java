package application.ui.users.components.grids;

import application.backend.users.models.SpecificUser;
import application.backend.users.services.SpecificUserService;
import application.ui.users.components.cards.profiles.UserProfile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Getter;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ProfileGrid<T extends SpecificUser<?>> extends VerticalLayout {

    @Getter
    private final Grid<T> grid;
    private final TextField searchByName = new TextField();
    private final TextField searchBySchoolDetails = new TextField();

    private final SpecificUserService<T> service;
    private final Function<T, UserProfile<T>> cardFactory;
    private final BiFunction<String, String, List<T>> searchFunction;

    public ProfileGrid(String header,
                       Class<T> beanType,
                       SpecificUserService<T> service,
                       Function<T, UserProfile<T>> cardFactory,
                       BiFunction<String, String, List<T>> searchFunction) {
        this.grid = new Grid<>(beanType);
        this.service = service;
        this.cardFactory = cardFactory;
        this.searchFunction = searchFunction;

        addClassName("profile-grid");

        configureGrid(header);
        configureSearchFields();

        add(createToolBar(), grid);
        setFlexGrow(1, grid);

        updateGrid();
    }

    private void configureGrid(String header) {
        grid.addClassName("no-padding-cell");
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setColumns();
        grid.addComponentColumn(this.cardFactory::apply)
            .setHeader(new H3(header))
            .setFlexGrow(1)
            .setAutoWidth(true);
    }

    private void configureSearchFields() {
        configureSearchField(searchByName, "By name");
        configureSearchField(searchBySchoolDetails, "By school details");
        searchByName.addValueChangeListener(change -> updateGrid());
        searchBySchoolDetails.addValueChangeListener(change -> updateGrid());
    }

    private Component createToolBar() {
        FlexLayout toolBar = new FlexLayout(searchByName, searchBySchoolDetails);
        toolBar.addClassNames("profile-grid-toolbar");
        toolBar.setWidthFull();
        toolBar.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        return toolBar;
    }

    private void updateGrid() {
        String nameFilter = searchByName.getValue();
        String schoolDetailsFilter = searchBySchoolDetails.getValue();

        grid.setItems(searchFunction.apply(nameFilter, schoolDetailsFilter));
    }

    private void configureSearchField(TextField searchField, String label) {
        searchField.setWidthFull();
        searchField.setMaxWidth("300px");
        searchField.setLabel(label);
        searchField.setPlaceholder("Search...");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
    }
}

