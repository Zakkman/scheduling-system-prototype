package application.ui.calendar.components;

import application.backend.appointment.models.Appointment;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarCell extends CalendarCellBase {

    private final List<Appointment> assignedAppointments = new ArrayList<>();
    private final Div displayedDate = new Div();

    public CalendarCell() {
        CalendarCellStyles.setInitialDateDivStyle(displayedDate);
        add(displayedDate);

        addClickListener(c ->
            fireEvent(new CellClickedEvent(this, assignedDate))
        );
    }

    public void updateCellDate(LocalDate cellDate) {
        displayedDate.setText("");
        CalendarCellStyles.resetCalendarCellStyle(this);
        CalendarCellStyles.setStandardDateStyle(displayedDate);

        if (cellDate != null) {
            assignedDate = cellDate;
            displayedDate.setText(String.valueOf(cellDate.getDayOfMonth()));
            setCellState(cellDate);
        } else {
            addClassName(LumoUtility.Background.CONTRAST_20);
        }
    }

    private void setCellState(LocalDate cellDate) {
        LocalDate today = LocalDate.now();
        if (cellDate.isEqual(today)) {
            CalendarCellStyles.setCurrentDateStyle(displayedDate);
            setEnabled(true);
        } else if (cellDate.isBefore(today)) {
            addClassName(LumoUtility.Background.CONTRAST_30);
            setEnabled(false);
        } else {
            setEnabled(true);
        }
    }

    public void updateAppointments(List<Appointment> appointmentsList) {
        assignedAppointments.clear();
        assignedAppointments.addAll(appointmentsList);

        int appointmentCount = assignedAppointments.size();
        if (appointmentCount > 0) {
            String backgroundColor = CalendarCellColor.getColorForCount(appointmentCount);
            getStyle().set("background-color", backgroundColor);
        }
    }

}
