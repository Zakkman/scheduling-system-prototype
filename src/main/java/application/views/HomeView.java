package application.views;

import application.layouts.HomeLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = HomeLayout.class)
public class HomeView extends VerticalLayout {

    public HomeView() {
        add(new H1("Home"));
    }

}
