package net.vounty.vanatic.api;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.utils.server.ServerVersion;
import net.vounty.vanatic.Vanatic;
import net.vounty.vanatic.adapter.adapters.*;
import net.vounty.vanatic.event.VanaticPacketEvents;
import net.vounty.vanatic.inventory.VanaticHubInventory;
import net.vounty.vanatic.inventory.VanaticInventory;
import net.vounty.vanatic.logger.Logger;
import net.vounty.vanatic.logger.VanaticLogger;
import net.vounty.vanatic.utils.enums.Paths;

public class VanaticAPI implements API {

    private final Vanatic vanatic;

    private ServerVersion serverVersion;
    private Logger logger;

    private ConfigurationAdapter configurationAdapter;
    private DetectionAdapter detectionAdapter;
    private MessageAdapter messageAdapter;
    private NotificationAdapter notificationAdapter;
    private PunishAdapter punishAdapter;
    private UserAdapter userAdapter;

    private VanaticInventory hubInventory;

    private VanaticPacketEvents vanaticPacketEvents;

    VanaticAPI(Vanatic vanatic) {
        this.vanatic = vanatic;
    }

    public static VanaticAPI of(Vanatic vanatic) {
        return new VanaticAPI(vanatic);
    }

    @Override
    public void onMountVanatic() {
        this.logger = new VanaticLogger();
        this.serverVersion = ServerVersion.getVersion();

        if (this.getServerVersion().equals(ServerVersion.ERROR)) {
            this.getLogger().error("Can't detect the version of your server. Is your server version to new?");
            this.getVanatic().disablePlugin();
            return;
        }

        Paths.createPaths();

        this.vanaticPacketEvents = new VanaticPacketEvents(this);

        this.hubInventory = new VanaticHubInventory(this);

        this.configurationAdapter = new VanaticConfigurationAdapter(this);
        this.detectionAdapter = new VanaticDetectionAdapter(this);
        this.messageAdapter = new VanaticMessageAdapter(this);
        this.notificationAdapter = new VanaticNotificationAdapter(this);
        this.punishAdapter = new VanaticPunishAdapter(this);
        this.userAdapter = new VanaticUserAdapter(this);

        this.getPacketEvents().registerListener(this.getVanaticPacketEvents());

        this.getConfigurationAdapter().createDefaultConfigurations();
        this.getConfigurationAdapter().loadDefaultConfigurations();

        this.getMessageAdapter().updateLocale(this.getConfigurationAdapter().getVanaticConfiguration().getLocale());
        this.getDetectionAdapter().registerDetections();
        this.getMessageAdapter().sendConsoleMessage("vanatic.api", 0, this.getServerVersion().name());
    }

    @Override
    public void onShutdownVanatic() {
        this.getPacketEvents().unregisterListener(this.getVanaticPacketEvents());
        this.getConfigurationAdapter().saveDefaultConfigurations();
        this.getDetectionAdapter().unregisterDetections();
    }

    @Override
    public VanaticInventory getHubInventory() {
        return this.hubInventory;
    }

    @Override
    public ConfigurationAdapter getConfigurationAdapter() {
        return this.configurationAdapter;
    }

    @Override
    public DetectionAdapter getDetectionAdapter() {
        return this.detectionAdapter;
    }

    @Override
    public MessageAdapter getMessageAdapter() {
        return messageAdapter;
    }

    @Override
    public NotificationAdapter getNotificationAdapter() {
        return this.notificationAdapter;
    }

    @Override
    public PunishAdapter getPunishAdapter() {
        return this.punishAdapter;
    }

    @Override
    public UserAdapter getUserAdapter() {
        return this.userAdapter;
    }

    @Override
    public VanaticPacketEvents getVanaticPacketEvents() {
        return this.vanaticPacketEvents;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public Vanatic getVanatic() {
        return this.vanatic;
    }

    @Override
    public ServerVersion getServerVersion() {
        return this.serverVersion;
    }

    @Override
    public PacketEvents getPacketEvents() {
        return this.getVanatic().getPacketEvents();
    }

}