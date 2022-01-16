package at.ac.univie.se2.ws21.team0404.app.utils.factory;

import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

public enum EChartType {
    BAR("Bar"), PIE("Pie");

    private final String name;

    EChartType(String name){
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
