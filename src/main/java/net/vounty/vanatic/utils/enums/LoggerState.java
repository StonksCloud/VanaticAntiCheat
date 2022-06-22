package net.vounty.vanatic.utils.enums;

public enum LoggerState {

    INFO("§1"),
    WARNING("§6"),
    ERROR("§4"),
    DEBUG("§d"),
    TRACE("§5");

    private final String color;

    LoggerState(String color) {
        this.color = color;
    }

    public String format() {
        return this.color + this.name();
    }

}