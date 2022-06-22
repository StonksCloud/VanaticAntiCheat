package net.vounty.vanatic.locale;

import java.util.List;
import java.util.Optional;

public class VanaticLocale implements Locale {

    private final String name, code;
    private final List<VanaticLocaleContent> values;

    public VanaticLocale(String name, String code, List<VanaticLocaleContent> values) {
        this.name = name;
        this.code = code;
        this.values = values;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public Integer getAmountFromContent(String key) {
        final Optional<LocaleContent> optionalLocaleContent = this.getContent(key);
        if (optionalLocaleContent.isPresent()) {
            final LocaleContent localeContent = optionalLocaleContent.get();
            return localeContent.isLine() ? 0 : localeContent.getLines().size();
        }
        return 0;
    }

    @Override
    public String convert(String key, Integer number, Object... arguments) {
        final Optional<LocaleContent> optionalLocaleContent = this.getContent(key);
        if (optionalLocaleContent.isPresent()) {
            final LocaleContent localeContent = optionalLocaleContent.get();
            return this.replace(localeContent.isLine() ? localeContent.getLine() : localeContent.getLines().get(number), arguments);
        }
        return key;
    }

    @Override
    public String convert(String key, Object... arguments) {
        return this.convert(key, 0, arguments);
    }

    @Override
    public String replace(String message, Object... arguments) {
        for (int count = 0; count < arguments.length; count++)
            message = message.replace("[#" + count + "]", String.valueOf(arguments[count]));
        return message;
    }

    @Override
    public Optional<LocaleContent> getContent(String key) {
        for (final VanaticLocaleContent content : this.getValues()) {
            if (content.getKey().equals(key)) {
                return Optional.of(content);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<VanaticLocaleContent> getValues() {
        return this.values;
    }

}
