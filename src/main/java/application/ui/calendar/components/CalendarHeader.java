package application.ui.calendar.components;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Locale;

public class CalendarHeader extends HorizontalLayout {

    private final H2 monthTitle = new H2();
    private final HorizontalLayout buttonLayout = new HorizontalLayout();

    private final Button currentMonthButton = new Button();
    private final Button nextMonthButton = new Button();

    public CalendarHeader() {
        setWidthFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        setAlignItems(FlexComponent.Alignment.CENTER);
        getStyle().set("padding-left", "8px");

        createButtonLayout();
        add(monthTitle, buttonLayout);
    }

    private void createButtonLayout() {
        buttonLayout.setSpacing(true);
        buttonLayout.add(currentMonthButton, nextMonthButton);

        setMonthNavigationButtonStyling(currentMonthButton);
        setMonthNavigationButtonStyling(nextMonthButton);

        currentMonthButton.setIcon(new Icon(VaadinIcon.ARROW_LEFT));
        nextMonthButton.setIcon(new Icon(VaadinIcon.ARROW_RIGHT));

        currentMonthButton.addClickListener(click ->
            fireEvent(new NavigateCurrentMonthEvent(this))
        );
        nextMonthButton.addClickListener(click ->
            fireEvent(new NavigateNextMonthEvent(this))
        );
    }

    private void setMonthNavigationButtonStyling(Button button) {
        button.removeClassName("lumo-button");
        button.addClassName("calendar-header-month-navigation-button-styling");
    }

    public void updateMonthTitle(YearMonth yearMonth) {
        monthTitle.setText(yearMonth
            .getMonth()
            .getDisplayName(java.time.format.TextStyle.FULL, Locale.US) + " " + yearMonth.getYear());
    }

    public void updateMonthNavigation(LocalDate displayedDate) {
        YearMonth currentMonth = YearMonth.now();
        YearMonth nextMonth = currentMonth.plusMonths(1);

        boolean isCurrentMonth = YearMonth.from(displayedDate).equals(currentMonth);
        if (isCurrentMonth) {
            currentMonthButton.getStyle().set("visibility", "hidden");
        } else {
            currentMonthButton.getStyle().set("visibility", "visible");
        }

        boolean isNextMonth = YearMonth.from(displayedDate).equals(nextMonth);
        if (isNextMonth) {
            nextMonthButton.getStyle().set("visibility", "hidden");
        } else {
            nextMonthButton.getStyle().set("visibility", "visible");
        }
    }

    public static abstract class MonthNavigationEvent extends ComponentEvent<CalendarHeader> {
        protected MonthNavigationEvent(CalendarHeader source) { super(source, false); }
    }

    public static class NavigateCurrentMonthEvent extends MonthNavigationEvent {
        NavigateCurrentMonthEvent(CalendarHeader source) { super(source); }
    }

    public static class NavigateNextMonthEvent extends MonthNavigationEvent {
        NavigateNextMonthEvent(CalendarHeader source) { super(source); }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
