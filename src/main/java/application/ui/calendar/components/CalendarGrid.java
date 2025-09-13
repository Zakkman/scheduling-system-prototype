package application.ui.calendar.components;

import application.backend.appointment.models.Appointment;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarGrid extends FlexLayout {

    private final List<CalendarCell> calendarCells = new ArrayList<>();

    public CalendarGrid(String[] daysOfWeek) {
        setFlexWrap(FlexLayout.FlexWrap.WRAP);
        setJustifyContentMode(FlexLayout.JustifyContentMode.CENTER);
        getStyle().set("gap", "0px");
        setWidthFull();

        createDayHeader(daysOfWeek);
        createCells();
    }

    private void createDayHeader(String[] daysOfWeek) {
        for (String day : daysOfWeek) {
            Span daySpan = new Span(day);
            daySpan.addClassName("calendar-day-header-day-text");

            add(daySpan);
        }
    }

    private void createCells() {
        for (int n = 1; n <= 42; n++) {
            CalendarCell dayCell = new CalendarCell();
            dayCell.addClassName("calendar-cell-sizing-style");

            add(dayCell);
            calendarCells.add(dayCell);
        }
    }

    public void updateCalendarGridDates(LocalDate date, YearMonth yearMonth) {
        LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        DayOfWeek firstDayOfWeekOfMonth = firstDayOfMonth.getDayOfWeek();

        int lengthOfMonth = yearMonth.lengthOfMonth();
        int startOffset = firstDayOfWeekOfMonth.getValue() % 7;

        int dayNum = 1;
        for (int c = 0; c < 42; c++) {
            if (c >= startOffset && dayNum <= lengthOfMonth) {
                LocalDate cellDate = firstDayOfMonth.plusDays(dayNum - 1);
                calendarCells.get(c).updateCellDate(cellDate);
                dayNum++;
            }else {
                calendarCells.get(c).updateCellDate(null);
            }
        }
    }

    public void updateCalendarGridAppointments(List<Appointment> appointmentsList) {
        calendarCells.forEach(cell -> {
            List<Appointment> matchedAppointments = appointmentsList.stream()
                .filter(appointment -> !cell.isEmpty() && cell.matchesDate(appointment))
                .collect(Collectors.toList());
            cell.updateAppointments(matchedAppointments);
        });
    }
}
