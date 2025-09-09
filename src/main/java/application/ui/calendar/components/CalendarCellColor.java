package application.ui.calendar.components;

public enum CalendarCellColor {
    ZERO_APPOINTMENTS("white"),
    FEW_APPOINTMENTS("#d4edda"),
    MEDIUM_APPOINTMENTS("#e0f2f7"),
    MANY_APPOINTMENTS("#fff3cd"),
    HIGH_APPOINTMENT_LOAD("#f8d7da"),
    FULL_SCHEDULE("#e6ccff");

    private final String hexCode;

    CalendarCellColor(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getHexCode() {
        return this.hexCode;
    }

    public static String getColorForCount(int count) {
        if (count == 0) return ZERO_APPOINTMENTS.getHexCode();
        if (count >= 1 && count <= 3) return FEW_APPOINTMENTS.getHexCode();
        if (count >= 4 && count <= 6) return MEDIUM_APPOINTMENTS.getHexCode();
        if (count >= 7 && count <= 9) return MANY_APPOINTMENTS.getHexCode();
        if (count >= 10 && count <= 14) return HIGH_APPOINTMENT_LOAD.getHexCode();
        return FULL_SCHEDULE.getHexCode();
    }
}
