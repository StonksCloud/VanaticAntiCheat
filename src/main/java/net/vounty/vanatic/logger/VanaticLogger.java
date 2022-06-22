package net.vounty.vanatic.logger;

import net.vounty.vanatic.utils.enums.ConsoleColor;
import net.vounty.vanatic.utils.enums.LoggerState;

import java.util.Collections;

public class VanaticLogger implements Logger {

    @Override
    public void info(String message) {
        this.info(message, Collections.emptyList());
    }

    @Override
    public void info(String message, Object... replacements) {
        this.log(LoggerState.INFO, message, replacements);
    }

    @Override
    public void warn(String message) {
        this.warn(message, Collections.emptyList());
    }

    @Override
    public void warn(String message, Object... replacements) {
        this.log(LoggerState.WARNING, message, replacements);
    }

    @Override
    public void error(String message) {
        this.error(message, Collections.emptyList());
    }

    @Override
    public void error(String message, Object... replacements) {
        this.log(LoggerState.ERROR, message, replacements);
    }

    @Override
    public void debug(String message) {
        this.debug(message, Collections.emptyList());
    }

    @Override
    public void debug(String message, Object... replacements) {
        this.log(LoggerState.DEBUG, message, replacements);
    }

    @Override
    public void trace(Throwable throwable) {
        this.trace(LoggerState.TRACE, throwable);
    }

    @Override
    public void trace(LoggerState state, Throwable throwable) {
        this.log(state, throwable.getMessage(), Collections.emptyList());
    }

    @Override
    public void log(LoggerState state, String message) {
        this.log(state, message, Collections.emptyList());
    }

    @Override
    public void log(LoggerState state, String message, Object... replacements) {
        System.out.println(this.format(state, replacements.length > 0 ? String.format(message, replacements) : message));
    }

    private String format(LoggerState state, String message) {
        return ConsoleColor.replace(("§7[%format%§7]§r " + message).replace("%format%", state.format()));
    }

}
