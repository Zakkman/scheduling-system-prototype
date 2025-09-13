package application.ui.calendar.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class CalendarCellStyles {

    public static void setInitialDateDivStyle(Div displayedDate) {
        displayedDate.addClassName("calendar-cell-initial-date-style");
    }

    public static void setStandardDateStyle(Div displayedDate) {
        displayedDate.removeClassNames(
            LumoUtility.Background.PRIMARY,
            LumoUtility.TextColor.PRIMARY_CONTRAST,
            LumoUtility.BorderRadius.LARGE
        );
    }

    public static void setCurrentDateStyle(Div displayedDate) {
        displayedDate.addClassNames(
            LumoUtility.Background.PRIMARY,
            LumoUtility.TextColor.PRIMARY_CONTRAST,
            LumoUtility.BorderRadius.LARGE,
            "calendar-cell-current-date-style"
        );
    }

    public static void resetCalendarCellStyle(CalendarCellBase cell) {
        cell.removeClassNames(
            LumoUtility.Background.CONTRAST_20,
            LumoUtility.Background.CONTRAST_30,
            LumoUtility.Background.PRIMARY,
            LumoUtility.TextColor.PRIMARY_CONTRAST
        );
        cell.getStyle().remove("background-color");
    }
}
