package net.vounty.vanatic.locale;

import java.util.List;

public class VanaticLocaleContent implements LocaleContent {

    private final String key;
    private final String line;
    private final List<String> lines;

    public VanaticLocaleContent(String key, String line) {
        this.key = key;
        this.line = line;
        this.lines = null;
    }

    public VanaticLocaleContent(String key, List<String> lines) {
        this.key = key;
        this.line = null;
        this.lines = lines;
    }

    @Override
    public Boolean isLine() {
        return this.getLine() != null;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getLine() {
        return this.line;
    }

    @Override
    public List<String> getLines() {
        return this.lines;
    }

}
