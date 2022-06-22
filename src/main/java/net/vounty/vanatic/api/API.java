package net.vounty.vanatic.api;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.utils.server.ServerVersion;
import net.vounty.vanatic.Vanatic;
import net.vounty.vanatic.adapter.adapters.*;
import net.vounty.vanatic.event.VanaticPacketEvents;
import net.vounty.vanatic.inventory.VanaticInventory;
import net.vounty.vanatic.logger.Logger;

public interface API {

    /**
     * This method is executed when the plugin is initialized.
     */
    void onMountVanatic();

    /**
     * This method is executed when the plugin is terminating.
     */
    void onShutdownVanatic();

    /**
     * Get the VanaticInventory instance.
     * @return - Returns the {@VanaticInventory} instance.
     */
    VanaticInventory getHubInventory();

    /**
     * Get the ConfigurationAdapter instance.
     * @return - Returns the {@ConfigurationAdapter} instance.
     */
    ConfigurationAdapter getConfigurationAdapter();

    /**
     * Get the DetectionAdapter instance.
     * @return - Returns the {@DetectionAdapter} instance.
     */
    DetectionAdapter getDetectionAdapter();

    /**
     * Get the MessageAdapter instance.
     * @return - Returns the {@MessageAdapter} instance.
     */
    MessageAdapter getMessageAdapter();

    /**
     * Get the NotificationAdapter instance.
     * @return - Returns the {@NotificationAdapter} instance.
     */
    NotificationAdapter getNotificationAdapter();

    /**
     * Get the PunishAdapter instance.
     * @return - Returns the {@PunishAdapter} instance.
     */
    PunishAdapter getPunishAdapter();

    /**
     * Get the UserAdapter instance.
     * @return - Returns the {@UserAdapter} instance.
     */
    UserAdapter getUserAdapter();

    /**
     * Get the VanaticPacketEvents instance.
     * @return - Returns the {@VanaticPacketEvents} instance.
     */
    VanaticPacketEvents getVanaticPacketEvents();

    /**
     * Get the Logger instance.
     * @return - Returns the {@Logger} instance.
     */
    Logger getLogger();

    /**
     * Get the Vanatic instance.
     * @return - Returns the {@Vanatic} instance.
     */
    Vanatic getVanatic();

    /**
     * Get the ServerVersion instance.
     * @return - Returns the {@ServerVersion} instance.
     */
    ServerVersion getServerVersion();

    /**
     * Get the PacketEvents instance.
     * @return - Returns the {@PacketEvents} instance.
     */
    PacketEvents getPacketEvents();

}
