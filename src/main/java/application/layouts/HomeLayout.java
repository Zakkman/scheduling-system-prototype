package application.layouts;

import application.views.HomeView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.RouterLink;

public class HomeLayout extends AppLayout {

    public HomeLayout() {
        setPrimarySection(Section.NAVBAR);

        H2 title = new H2("Scheduling System");

        Tab homeTab = new Tab(new RouterLink("Home", HomeView.class));

        HorizontalLayout tabs = new HorizontalLayout(homeTab);
        HorizontalLayout header = new HorizontalLayout(title, tabs);
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        addToNavbar(header);
    }

}
