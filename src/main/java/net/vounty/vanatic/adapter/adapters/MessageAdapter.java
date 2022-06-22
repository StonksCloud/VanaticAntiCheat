package net.vounty.vanatic.adapter.adapters;

import net.vounty.vanatic.locale.Locale;
import org.bukkit.command.CommandSender;

public interface MessageAdapter {

    void updateLocale(String locale);

    void sendMessage(CommandSender sender, String message, Integer amount, Object... replacements);
    void sendMessage(CommandSender sender, String message, Object... replacements);
    void sendConsoleMessage(String message, Object... replacements);
    void sendConsoleMessage(String message, Integer amount, Object... replacements);

    String convert(String key, Integer number, Object... arguments);
    String convert(String key, Object... arguments);
    String replace(String message, Object... arguments);

    String formatLocale(String key, Integer number, Object... arguments);
    String formatLocale(String key, Object... arguments);

    String format(String message, Boolean withPrefix);
    String format(String message);
    String getPrefix();

    Locale getCurrentLocale();

}