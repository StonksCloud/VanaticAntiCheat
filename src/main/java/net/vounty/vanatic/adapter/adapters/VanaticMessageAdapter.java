package net.vounty.vanatic.adapter.adapters;

import net.vounty.vanatic.VanaticPlugin;
import net.vounty.vanatic.adapter.VanaticAdapter;
import net.vounty.vanatic.api.API;
import net.vounty.vanatic.locale.Locale;
import net.vounty.vanatic.utils.configuration.VanaticConfiguration;
import net.vounty.vanatic.utils.enums.BukkitColor;
import net.vounty.vanatic.utils.enums.ConsoleColor;
import net.vounty.vanatic.utils.enums.VanaticLocale;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.InputStream;

public class VanaticMessageAdapter extends VanaticAdapter implements MessageAdapter {

    private Locale currentLocale;

    public VanaticMessageAdapter(API api) {
        super(api);
    }

    @Override
    public void updateLocale(String locale) {
        final VanaticLocale vanaticLocale = VanaticLocale.getOrDefault(locale, VanaticLocale.ENGLISH);
        final InputStream inputStream = VanaticPlugin.getService().getClass().getResourceAsStream("/locales/" + vanaticLocale.name() + ".json");
        this.currentLocale = this.getAPI().getConfigurationAdapter().readConfiguration(inputStream, net.vounty.vanatic.locale.VanaticLocale.class);
    }

    @Override
    public void sendMessage(CommandSender sender, String message, Integer amount, Object... replacements) {
        sender.sendMessage(BukkitColor.replace(this.getPrefix() + this.format(this.currentLocale.convert(message, amount, replacements))));
    }

    @Override
    public void sendMessage(CommandSender sender, String message, Object... replacements) {
        this.sendMessage(sender, message, 0, replacements);
    }

    @Override
    public void sendConsoleMessage(String message, Integer amount, Object... replacements) {
        this.sendMessage(Bukkit.getConsoleSender(), message, amount, replacements);
    }

    @Override
    public void sendConsoleMessage(String message, Object... replacements) {
        this.sendConsoleMessage(message, 0, replacements);
    }

    @Override
    public String convert(String key, Integer number, Object... arguments) {
        return this.currentLocale.convert(key, number, arguments);
    }

    @Override
    public String convert(String key, Object... arguments) {
        return this.currentLocale.convert(key, arguments);
    }

    @Override
    public String replace(String message, Object... arguments) {
        return this.currentLocale.replace(message, arguments);
    }

    @Override
    public String formatLocale(String key, Integer number, Object... arguments) {
        return this.format(this.convert(key, number, arguments), false);
    }

    @Override
    public String formatLocale(String key, Object... arguments) {
        return this.format(this.convert(key, 0, arguments), false);
    }

    @Override
    public String format(String message, Boolean withPrefix) {
        final VanaticConfiguration configuration = this.getAPI().getConfigurationAdapter().getVanaticConfiguration();
        return (withPrefix ? this.getPrefix() : "") + message
                .replace("{prefix}", "§" + BukkitColor.getOrDefault(configuration.getPrefix(), BukkitColor.DARK_BLUE).getCode())
                .replace("{suffix}", "§" + BukkitColor.getOrDefault(configuration.getSuffix(), BukkitColor.INDIGO).getCode());
    }

    @Override
    public String format(String message) {
        return this.format(message, false);
    }

    @Override
    public String getPrefix() {
        return this.format(" §8•║ {prefix}§lV{suffix}anatic §8› ");
    }

    @Override
    public Locale getCurrentLocale() {
        return this.currentLocale;
    }

}