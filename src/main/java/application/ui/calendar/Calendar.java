package application.ui.calendar;

import application.backend.appointment.services.AppointmentService;
import application.backend.users.models.User;
import application.ui.calendar.components.CalendarGrid;
import application.ui.calendar.components.CalendarHeader;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDate;
import java.time.YearMonth;

public class Calendar extends VerticalLayout {

    private final AppointmentService service;
    private final User currentUser;

    private final CalendarHeader header;
    private final CalendarGrid grid;

    private LocalDate date;

    public Calendar(AppointmentService service, User currentUser) {
        this.service = service;
        this.currentUser = currentUser;
        addClassName("calendar");
        setAlignItems(FlexComponent.Alignment.CENTER);

        header = new CalendarHeader();
        grid = new CalendarGrid(new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"});
        date = LocalDate.now();
        add(header, grid);

        connectMonthNavigation();
        updateCalendar();
    }

    private void connectMonthNavigation() {
        header.addListener(CalendarHeader.NavigateCurrentMonthEvent.class, this::resetMonth);
        header.addListener(CalendarHeader.NavigateNextMonthEvent.class, this::nextMonth);
    }

    private void resetMonth(CalendarHeader.NavigateCurrentMonthEvent navigateCurrentMonthEvent) {
        date = LocalDate.now();
        updateCalendar();
    }

    private void nextMonth(CalendarHeader.NavigateNextMonthEvent navigateNextMonthEvent) {
        date = date.plusMonths(1);
        updateCalendar();
    }

    private void updateCalendar() {
        YearMonth yearMonth = YearMonth.from(date);
        header.updateMonthTitle(yearMonth);
        header.updateMonthNavigation(date);
        grid.updateCalendarGridDates(date, yearMonth);
        grid.updateCalendarGridAppointments(service.findAppointmentsForUserAtMonth(currentUser, yearMonth));
    }

}
