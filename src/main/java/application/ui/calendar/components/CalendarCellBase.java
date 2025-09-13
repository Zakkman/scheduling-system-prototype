package application.ui.calendar.components;

import application.backend.appointment.models.Appointment;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.LocalDate;

public class CalendarCellBase extends VerticalLayout {

    protected LocalDate assignedDate;

    public CalendarCellBase() {
        applyBaseStyling();
        applyMouseHover();
    }

    private void applyBaseStyling() {
        setPadding(false);
        setAlignItems(Alignment.CENTER);
        addClassName("calendar-cell-base-style");

        addClassNames(LumoUtility.Border.ALL);
    }

    private void applyMouseHover() {
        addAttachListener(event -> {
            getElement().addEventListener("mouseenter", e -> {
                if (assignedDate != null) {
                    addClassName(LumoUtility.Background.CONTRAST_30);
                }
            });
            getElement().addEventListener("mouseleave", e -> {
                if (assignedDate != null) {
                    removeClassName(LumoUtility.Background.CONTRAST_30);
                }
            });
        });
    }

    public boolean isEmpty() {
        return assignedDate == null;
    }

    public boolean matchesDate(Appointment appointment) {
        return appointment.getDate().equals(assignedDate);
    }

    public static class CellClickedEvent extends ComponentEvent<CalendarCell> {
        private final LocalDate clickedDate;

        public CellClickedEvent(CalendarCell source, LocalDate clickedDate) {
            super(source, false);
            this.clickedDate = clickedDate;
        }

        public LocalDate getClickedDate() {
            return clickedDate;
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                           ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
