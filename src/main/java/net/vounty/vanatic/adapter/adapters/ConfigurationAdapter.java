package net.vounty.vanatic.adapter.adapters;

import com.google.gson.Gson;
import net.vounty.vanatic.utils.configuration.VanaticConfiguration;
import net.vounty.vanatic.utils.enums.Paths;

import java.io.InputStream;

public interface ConfigurationAdapter {

    void createDefaultConfigurations();
    void loadDefaultConfigurations();
    void saveDefaultConfigurations();

    void saveConfiguration(String filePath, Object document, Boolean override);
    void saveConfiguration(Paths paths, Object document, Boolean override);
    <Type> Type readConfiguration(String filePath, Class<Type> type);
    <Type> Type readConfiguration(Paths paths, Class<Type> type);
    <Type> Type readConfiguration(InputStream inputStream, Class<Type> type);

    Gson generateBuilder();
    VanaticConfiguration getVanaticConfiguration();

}