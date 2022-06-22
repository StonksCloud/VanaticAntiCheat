package net.vounty.vanatic.logger;

import net.vounty.vanatic.utils.enums.LoggerState;

public interface Logger {

    void info(String message);

    void info(String message, Object... replacements);

    void warn(String message);

    void warn(String message, Object... replacements);

    void error(String message);

    void error(String message, Object... replacements);

    void debug(String message);

    void debug(String message, Object... replacements);

    void trace(Throwable throwable);

    void trace(LoggerState state, Throwable throwable);

    void log(LoggerState state, String message);

    void log(LoggerState state, String message, Object... replacements);

}