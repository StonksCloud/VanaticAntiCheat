package net.vounty.vanatic.utils.configuration;

import net.vounty.vanatic.utils.enums.BukkitColor;
import net.vounty.vanatic.utils.enums.DatabaseType;
import net.vounty.vanatic.utils.enums.VanaticLocale;

public class VanaticConfiguration {

    private Boolean isEnabled;
    private String prefix, suffix;
    private String locale;
    private final DataBase dataBase;

    public VanaticConfiguration(Boolean isEnabled, String prefix, String suffix,
                                String locale, DataBase dataBase) {
        this.isEnabled = isEnabled;
        this.prefix = prefix;
        this.suffix = suffix;
        this.locale = locale;
        this.dataBase = dataBase;
    }

    public static VanaticConfiguration getDefaultConfiguration() {
        return new VanaticConfiguration(true, BukkitColor.DARK_BLUE.name(), BukkitColor.INDIGO.name(),
                VanaticLocale.ENGLISH.name(), DataBase.getDefaultConfiguration());
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Boolean isEnabled() {
        return this.isEnabled;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getLocale() {
        return this.locale;
    }

    public DataBase getDataBase() {
        return this.dataBase;
    }

    public static class DataBase {

        private final DatabaseType type;
        private final String hostName;
        private final Integer hostPort;
        private final String user, password, database;

        public DataBase(DatabaseType type, String hostName, Integer hostPort,
                        String user, String password, String database) {
            this.type = type;
            this.hostName = hostName;
            this.hostPort = hostPort;
            this.user = user;
            this.password = password;
            this.database = database;
        }

        public static DataBase getDefaultConfiguration() {
            return new DataBase(DatabaseType.JSON, "localhost", 0,
                    "root", "", "vanatic");
        }

        public DatabaseType getType() {
            return this.type;
        }

        public String getHostName() {
            return this.hostName;
        }

        public Integer getHostPort() {
            return this.hostPort;
        }

        public String getUser() {
            return this.user;
        }

        public String getPassword() {
            return this.password;
        }

        public String getDatabase() {
            return this.database;
        }

    }

}