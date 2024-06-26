package dev.symo.finz.util;

public enum Category {
    BLOCKS("Blocks"),
    MOVEMENT("Movement"),
    COMBAT("Combat"),
    RENDER("Render"),
    CHAT("Chat"),
    FUN("Fun"),
    ITEMS("Items"),
    OTHER("Other"),
    SETTINGS("Settings");

    private final String name;

    private Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}