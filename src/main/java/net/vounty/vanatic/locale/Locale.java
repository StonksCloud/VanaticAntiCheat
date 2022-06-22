package net.vounty.vanatic.locale;

import java.util.List;
import java.util.Optional;

public interface Locale {

    String convert(String key, Integer number, Object... arguments);
    String convert(String key, Object... arguments);
    String replace(String message, Object... arguments);

    Integer getAmountFromContent(String key);
    Optional<LocaleContent> getContent(String key);

    String getName();
    String getCode();
    List<VanaticLocaleContent> getValues();

}
