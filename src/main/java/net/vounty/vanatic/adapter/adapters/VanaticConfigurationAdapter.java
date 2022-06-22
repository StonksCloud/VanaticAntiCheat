package net.vounty.vanatic.adapter.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import net.vounty.vanatic.adapter.VanaticAdapter;
import net.vounty.vanatic.api.API;
import net.vounty.vanatic.utils.configuration.VanaticConfiguration;
import net.vounty.vanatic.utils.enums.Paths;

import java.io.*;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;

public class VanaticConfigurationAdapter extends VanaticAdapter implements ConfigurationAdapter {

    private VanaticConfiguration vanaticConfiguration;

    public VanaticConfigurationAdapter(API api) {
        super(api);
    }

    @Override
    public void createDefaultConfigurations() {
        this.saveConfiguration(Paths.VANATIC_CONFIGURATION, VanaticConfiguration.getDefaultConfiguration(), false);
    }

    @Override
    public void loadDefaultConfigurations() {
        this.vanaticConfiguration = this.readConfiguration(Paths.VANATIC_CONFIGURATION, VanaticConfiguration.class);
    }

    @Override
    public void saveDefaultConfigurations() {
        if (this.vanaticConfiguration != null) {
            this.saveConfiguration(Paths.VANATIC_CONFIGURATION, this.vanaticConfiguration, true);
        }
    }

    @Override
    public void saveConfiguration(String filePath, Object document, Boolean override) {
        try {
            final File currentFile = new File(filePath);
            if (override) {
                if (!currentFile.exists()) currentFile.createNewFile();
                final Writer writer = new PrintWriter(currentFile, StandardCharsets.UTF_8);
                this.generateBuilder().toJson(document, writer);
                writer.close();
                return;
            }
            if (!currentFile.exists()) {
                currentFile.createNewFile();
                final Writer writer = new PrintWriter(currentFile, StandardCharsets.UTF_8);
                this.generateBuilder().toJson(document, writer);
                writer.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void saveConfiguration(Paths paths, Object document, Boolean override) {
        this.saveConfiguration(paths.getPath(), document, override);
    }

    @Override
    public <Type> Type readConfiguration(String filePath, Class<Type> type) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            return this.readConfiguration(fileInputStream, type);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public <Type> Type readConfiguration(Paths paths, Class<Type> type) {
        return this.readConfiguration(paths.getPath(), type);
    }

    @Override
    public <Type> Type readConfiguration(InputStream inputStream, Class<Type> type) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            return generateBuilder().fromJson(inputStreamReader, type);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        } finally {
            try {
                if (inputStreamReader != null)
                    inputStreamReader.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public Gson generateBuilder() {
        return new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .serializeNulls()
                .serializeSpecialFloatingPointValues()
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .create();
    }

    @Override
    public VanaticConfiguration getVanaticConfiguration() {
        return this.vanaticConfiguration;
    }

}