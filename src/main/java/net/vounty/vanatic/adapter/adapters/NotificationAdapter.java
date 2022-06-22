package net.vounty.vanatic.adapter.adapters;

import net.vounty.vanatic.detection.Detection;
import net.vounty.vanatic.detection.value.Value;
import org.bukkit.entity.Player;

public interface NotificationAdapter {

    void toggleNotifications(Player player);
    void toggleDebug(Player player, String detectionName, Boolean disableDebug);

    void sendFlagNotification(Player target, Detection detection, Value... values);
    void sendDebugNotification(Player player, Detection detection, Value... values);

}