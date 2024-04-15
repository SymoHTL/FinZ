package dev.symo.finz.util;

public enum InputType {
    TEXT("text"),
    //NUMBER("number"),
    BOOLEAN("boolean"),
    //SELECT("select"),
    DECIMAL("decimal"),
    //COLOR("color"),
    DECIMAL_SLIDER("decimal-slider"),
    PERCENT_SLIDER("percent-slider");

    private final String name;

    private InputType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
