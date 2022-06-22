package net.vounty.vanatic.adapter.adapters;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.vounty.vanatic.VanaticPlugin;
import net.vounty.vanatic.adapter.VanaticAdapter;
import net.vounty.vanatic.api.API;
import net.vounty.vanatic.detection.Detection;
import net.vounty.vanatic.detection.value.Value;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class VanaticNotificationAdapter extends VanaticAdapter implements NotificationAdapter {

    private final String key = "vanatic_notify",
                         debug_key = "vanatic_debug";

    public VanaticNotificationAdapter(API api) {
        super(api);
    }

    @Override
    public void toggleNotifications(Player player) {
        if (player.hasMetadata(key)) {
            player.removeMetadata(key, VanaticPlugin.getService());
            this.getAPI().getMessageAdapter().sendMessage(player, "vanatic.adapter.notification", 0);
        } else {
            player.setMetadata(key, new FixedMetadataValue(VanaticPlugin.getService(), true));
            this.getAPI().getMessageAdapter().sendMessage(player, "vanatic.adapter.notification", 1);
        }
    }

    @Override
    public void toggleDebug(Player player, String detectionName, Boolean disableDebug) {

        if (disableDebug && player.hasMetadata(debug_key)) {
            player.removeMetadata(debug_key, VanaticPlugin.getService());
            this.getAPI().getMessageAdapter().sendMessage(player, "vanatic.adapter.notification", 13);
            return;
        }

        if (player.hasMetadata(debug_key)) {
            final String currentDebugName = player.getMetadata(debug_key).get(0).asString();
            if (currentDebugName.equals(detectionName)) {
                player.removeMetadata(debug_key, VanaticPlugin.getService());
                this.getAPI().getMessageAdapter().sendMessage(player, "vanatic.adapter.notification", 13);
                return;
            }
        }

        final Optional<Detection> optionalDetection = this.getAPI().getDetectionAdapter().getDetection(detectionName);
        optionalDetection.ifPresentOrElse(detection -> {
            player.setMetadata(debug_key, new FixedMetadataValue(VanaticPlugin.getService(), detectionName));
            this.getAPI().getMessageAdapter().sendMessage(player, "vanatic.adapter.notification", 14);
        }, () -> this.getAPI().getMessageAdapter().sendMessage(player, "vanatic.adapter.notification", 15));
    }

    @Override
    public void sendDebugNotification(Player target, Detection detection, Value... values) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.hasMetadata(debug_key) &&
                                  player.getMetadata(debug_key).get(0).asString().equals(detection.getAbsoluteName()))
                .forEach(player -> {
                    final String chatFormat = this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 12, target.getName(), detection.getAbsoluteName());
                    final TextComponent textComponent = new TextComponent(this.getAPI().getMessageAdapter().format(chatFormat, true));
                    final ComponentBuilder componentBuilder = new ComponentBuilder(
                            this.getAPI().getMessageAdapter().format(
                                            "§8┌§m────────────────§r§8┐\n" +
                                            "§8├ " + this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 4) + " §8• {suffix}" + target.getName() + "\n" +
                                            "§8├ " + this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 8) + " §8• {suffix}" + detection.getAbsoluteName() + "\n" +
                                            "§8├§m────────────────§r§8┤\n" +
                                            this.buildStringFromInformation(values) +
                                            "§8├ " + this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 9) + " §8• {suffix}" + target.getWorld().getName() + "\n" +
                                            "§8├ " + this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 10) + " §8• {suffix}" + this.round(target.getLocation().getX()) + "§8, {suffix}" + this.round(target.getLocation().getY()) + "§8, {suffix}" + this.round(target.getLocation().getZ()) + "\n" +
                                            "§8├ " + this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 11) + "\n" +
                                            "§8└────────────────§r§8┘\n")
                    );
                    textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, componentBuilder.create()));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + target.getName()));
                    player.spigot().sendMessage(textComponent);
        });
    }

    @Override
    public void sendFlagNotification(Player target, Detection detection, Value... values) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.hasMetadata(key) || player.isOp() || player.hasPermission("vanatic.alert"))
                .forEach(player -> {
            final int violationAmount = detection.getViolations(target);
            final String violationFormat = this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 3, ("{suffix}|".repeat(violationAmount)), ("§7|".repeat(detection.getKickAmount() - violationAmount)), ("{suffix}|".repeat(violationAmount)), ("§7|".repeat(detection.getBanAmount() - violationAmount)));
            final String chatFormat = this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 2, target.getName(), detection.getAbsoluteName(), violationFormat);
            final TextComponent textComponent = new TextComponent(this.getAPI().getMessageAdapter().format(chatFormat, true));
            final ComponentBuilder componentBuilder = new ComponentBuilder(
                    this.getAPI().getMessageAdapter().format(
                                    "§8┌§m────────────────§r§8┐\n" +
                                    "§8├ " + this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 4) + " §8• {suffix}" + target.getName() + "\n" +
                                    "§8├ " + this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 5) + " §8• {suffix}" + this.round(this.getAPI().getPacketEvents().getPlayerUtils().getPing(target)) + "\n" +
                                    "§8├ " + this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 6) + " §8• {suffix}" + this.round(this.getAPI().getPacketEvents().getServerUtils().getTPS()) + "\n" +
                                    "§8├ " + this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 7) + " §8• {suffix}" + violationAmount + "\n" +
                                    "§8├ " + this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 8) + " §8• {suffix}" + detection.getAbsoluteName() + "\n" +
                                    "§8├§m────────────────§r§8┤\n" +
                                    this.buildStringFromInformation(values) +
                                    "§8├ " + this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 9) + " §8• {suffix}" + target.getWorld().getName() + "\n" +
                                    "§8├ " + this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 10) + " §8• {suffix}" + this.round(target.getLocation().getX()) + "§8, {suffix}" + this.round(target.getLocation().getY()) + "§8, {suffix}" + this.round(target.getLocation().getZ()) + "\n" +
                                    "§8├ " + this.getAPI().getMessageAdapter().formatLocale("vanatic.adapter.notification", 11) + "\n" +
                                    "§8└────────────────§r§8┘\n")
            );
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, componentBuilder.create()));
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + target.getName()));
            player.spigot().sendMessage(textComponent);
        });
    }

    private Integer round(double value) {
        return Math.toIntExact(Math.round(value));
    }

    private String buildStringFromInformation(Value... values) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final Value value : values) {
            stringBuilder
                    .append("§8├ §7")
                    .append(value.getPrefix())
                    .append(" §8• ")
                    .append("{suffix}")
                    .append(value.getValue())
                    .append("\n");
        }
        if (values.length > 0) stringBuilder.append("§8├§m────────────────§r§8┤\n");
        return stringBuilder.toString();
    }

}