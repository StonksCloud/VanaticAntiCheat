package net.vounty.vanatic.utils.enums;

import java.util.Locale;

public enum VanaticLocale {

    GERMAN, ENGLISH;

    public static VanaticLocale get(String value) {
        for (final VanaticLocale locale : VanaticLocale.values()) {
            if (locale.name().equals(value.toUpperCase(Locale.ROOT))) {
                return locale;
            }
        }
        return null;
    }

    public static VanaticLocale getOrDefault(String value, VanaticLocale defaultValue) {
        for (final VanaticLocale locale : VanaticLocale.values()) {
            if (locale.name().equals(value.toUpperCase(Locale.ROOT))) {
                return locale;
            }
        }
        return defaultValue;
    }

}