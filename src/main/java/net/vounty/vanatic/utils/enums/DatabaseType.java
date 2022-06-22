package net.vounty.vanatic.utils.enums;

import java.util.Locale;
import java.util.Optional;

public enum DatabaseType {

    JSON, MARIA, ARANGO, MONGO;

    public static Optional<DatabaseType> getLocale(String value) {
        for (final DatabaseType database : DatabaseType.values()) {
            if (database.name().equals(value.toUpperCase(Locale.ROOT))) {
                return Optional.of(database);
            }
        }
        return Optional.empty();
    }

}