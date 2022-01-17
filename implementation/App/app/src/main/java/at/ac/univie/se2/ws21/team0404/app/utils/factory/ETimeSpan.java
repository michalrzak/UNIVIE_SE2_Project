package at.ac.univie.se2.ws21.team0404.app.utils.factory;

import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

public enum ETimeSpan {
    DAYS_1(1, "24 Hours"), DAYS_7(7, "7 Days"),
    DAYS_30(30, "1 Month"), DAYS_180(180, "6 Months"),
    DAYS_365(365, "1 Year");

    private final int value;
    private final String text;

    ETimeSpan(int value, String text){
        this.value = value;
        this.text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return text;
    }

    public int getValue() {
        return value;
    }
}
