package net.vounty.vanatic.command;

import net.vounty.vanatic.VanaticPlugin;
import net.vounty.vanatic.api.API;
import net.vounty.vanatic.utils.enums.VanaticLocale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class VanaticCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command,
                             @Nonnull String label, @Nonnull String[] arguments) {

        if (!(commandSender instanceof Player)) {
            this.sendMessage(commandSender, "vanatic.command", 0);
            return false;
        }

        final Player player = (Player) commandSender;

        if (!player.hasPermission("vanatic.commands.use")) {
            this.sendMessage(player, "vanatic.command", 1);
            return false;
        }

        switch (arguments.length) {
            case 0 -> {
                for (int count = 0; count < this.getAPI().getMessageAdapter().getCurrentLocale().getAmountFromContent("vanatic.command.usage"); count++) {
                    this.sendMessage(player, "vanatic.command.usage", count);
                }
            }
            case 1 -> {
                switch (arguments[0].toLowerCase(Locale.ROOT)) {
                    case "ui", "gui", "userinterface", "graphicuserinterface" -> this.getAPI().getHubInventory().openInventory(player);
                    case "reset" -> this.getAPI().getDetectionAdapter().resetDetectionConfigurations(success -> {
                        if (success) {
                            this.sendMessage(player, "vanatic.command", 2);
                        } else this.sendMessage(player, "vanatic.command", 3);
                    });
                    case "notify", "alert", "notifications", "alerts" -> this.getAPI().getNotificationAdapter().toggleNotifications(player);
                    case "debug" -> this.getAPI().getNotificationAdapter().toggleDebug(player, "", true);
                    default -> this.sendMessage(player, "vanatic.command", 4);
                }
            }
            case 2 -> {
                switch (arguments[0].toLowerCase(Locale.ROOT)) {
                    case "debug" ->  this.getAPI().getNotificationAdapter().toggleDebug(player, arguments[1], false);
                    case "locale" -> {
                        final VanaticLocale vanaticLocale = VanaticLocale.get(arguments[1]);

                        if (vanaticLocale == null) {
                            this.sendMessage(player, "vanatic.command", 5);
                            this.sendMessage(player, "vanatic.command", 6);
                            for (final VanaticLocale locale : VanaticLocale.values()) {
                                player.sendMessage(this.getAPI().getMessageAdapter().getPrefix() + " §7" + locale.name());
                            }
                            break;
                        }

                        if (!vanaticLocale.name().equalsIgnoreCase(this.getAPI().getMessageAdapter().getCurrentLocale().getName())) {
                            this.getAPI().getMessageAdapter().updateLocale(vanaticLocale.name());
                            this.sendMessage(player, "vanatic.command", 8, vanaticLocale.name());
                            break;
                        }

                        this.sendMessage(player, "vanatic.command", 7);
                    }
                    default -> this.sendMessage(player, "vanatic.command", 4);
                }
            }
            default -> this.sendMessage(player, "vanatic.command", 4);
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command,
                                      @Nonnull String label, @Nonnull String[] arguments) {
        return new LinkedList<>();
    }

    private void sendMessage(CommandSender commandSender, String message, Integer amount, Object... replacements) {
        this.getAPI().getMessageAdapter().sendMessage(commandSender, message, amount, replacements);
    }

    private API getAPI() {
        return VanaticPlugin.getService().getAPI();
    }

}
