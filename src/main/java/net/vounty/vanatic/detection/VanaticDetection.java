package net.vounty.vanatic.detection;

import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.impl.*;
import io.github.retrooper.packetevents.packetwrappers.NMSPacket;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import net.vounty.vanatic.Vanatic;
import net.vounty.vanatic.VanaticPlugin;
import net.vounty.vanatic.api.API;
import net.vounty.vanatic.detection.option.Option;
import net.vounty.vanatic.detection.option.VanaticOption;
import net.vounty.vanatic.detection.value.Value;
import net.vounty.vanatic.user.User;
import net.vounty.vanatic.utils.annotations.DetectionInformation;
import net.vounty.vanatic.utils.enums.DetectionMode;
import net.vounty.vanatic.utils.enums.ProtocolMode;
import net.vounty.vanatic.utils.enums.ProtocolType;
import net.vounty.vanatic.utils.enums.Paths;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class VanaticDetection extends PacketListenerAbstract implements Detection, Listener {

    private final VanaticDetectionDescription description;
    private VanaticDetectionConfiguration configuration;
    private final List<VanaticOption> options;

    private final Integer kickAmount, banAmount;
    private Integer maxViolation;

    private final Map<Player, Integer> violationsMap;

    public VanaticDetection() {
        this.options = new LinkedList<>();
        this.violationsMap = new LinkedHashMap<>();

        final DetectionInformation information = this.getClass().getAnnotation(DetectionInformation.class);
        this.description = VanaticDetectionDescription.of(information);
        this.kickAmount = information.kickAmount();
        this.banAmount = information.banAmount();

        this.maxViolation = this.kickAmount;
        if (this.banAmount > this.maxViolation) {
            this.maxViolation = this.banAmount;
        }
    }

    @Override
    public void onInitialize() {}

    @Override
    public void onTerminate() {}

    @Override
    public void onMount() {}

    @Override
    public void onShutdown() {}

    @Override
    public abstract Boolean onPacket(User user, NMSPacket nmsPacket, WrappedPacket wrappedPacket, ProtocolMode mode, ProtocolType type);

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        event.setCancelled(this.onPacket(this.getAPI().getUserAdapter().getUserUnsafe(event.getPlayer()), event.getNMSPacket(), new WrappedPacket(event.getNMSPacket()), ProtocolMode.RECEIVE, ProtocolType.PLAY));
    }

    @Override
    public void onPacketPlaySend(PacketPlaySendEvent event) {
        event.setCancelled(this.onPacket(this.getAPI().getUserAdapter().getUserUnsafe(event.getPlayer()), event.getNMSPacket(), new WrappedPacket(event.getNMSPacket()), ProtocolMode.SEND, ProtocolType.PLAY));
    }

    @Override
    public void onPacketHandshakeReceive(PacketHandshakeReceiveEvent event) {
        event.setCancelled(this.onPacket(null, event.getNMSPacket(), new WrappedPacket(event.getNMSPacket()), ProtocolMode.RECEIVE, ProtocolType.HANDSHAKE));
    }

    @Override
    public void onPacketLoginReceive(PacketLoginReceiveEvent event) {
        event.setCancelled(this.onPacket(null, event.getNMSPacket(), new WrappedPacket(event.getNMSPacket()), ProtocolMode.RECEIVE, ProtocolType.LOGIN));
    }

    @Override
    public void onPacketLoginSend(PacketLoginSendEvent event) {
        event.setCancelled(this.onPacket(null, event.getNMSPacket(), new WrappedPacket(event.getNMSPacket()), ProtocolMode.SEND, ProtocolType.LOGIN));
    }

    @Override
    public void onPacketStatusReceive(PacketStatusReceiveEvent event) {
        event.setCancelled(this.onPacket(null, event.getNMSPacket(), new WrappedPacket(event.getNMSPacket()), ProtocolMode.RECEIVE, ProtocolType.STATUS));
    }

    @Override
    public void onPacketStatusSend(PacketStatusSendEvent event) {
        event.setCancelled(this.onPacket(null, event.getNMSPacket(), new WrappedPacket(event.getNMSPacket()), ProtocolMode.SEND, ProtocolType.STATUS));
    }

    @Override
    public void onPostPacketPlayReceive(PostPacketPlayReceiveEvent event) {
        this.onPacket(this.getAPI().getUserAdapter().getUserUnsafe(event.getPlayer()), event.getNMSPacket(), new WrappedPacket(event.getNMSPacket()), ProtocolMode.RECEIVE, ProtocolType.POST_PLAY);
    }

    @Override
    public void onPostPacketPlaySend(PostPacketPlaySendEvent event) {
        this.onPacket(this.getAPI().getUserAdapter().getUserUnsafe(event.getPlayer()), event.getNMSPacket(), new WrappedPacket(event.getNMSPacket()), ProtocolMode.SEND, ProtocolType.POST_PLAY);
    }

    @Override
    public Boolean isBlockedListen(Player player) {
        return this.getAPI().getDetectionAdapter().getMode(this).equals(DetectionMode.ENABLED) &&
                (!player.isOp() && player.hasPermission("vanatic.bypass"));
    }

    @Override
    public void debug(Player player, Value... values) {
        this.getAPI().getNotificationAdapter().sendDebugNotification(player, this, values);
    }

    @Override
    public void saveConfiguration(Boolean override) {
        this.getAPI().getConfigurationAdapter()
                .saveConfiguration(this.getAbsolutePath(), VanaticDetectionConfiguration.of(this), override);
        this.loadConfiguration();
    }

    @Override
    public void resetConfiguration() {
        this.saveConfiguration(true);
    }

    private void loadConfiguration() {
        this.configuration = this.getAPI().getConfigurationAdapter()
                .readConfiguration(this.getAbsolutePath(), VanaticDetectionConfiguration.class);
    }

    @Override
    public void registerOption(String key, Object value) {
        final Optional<Option> optionalOption = this.getOption(key);
        optionalOption.ifPresentOrElse(option -> option.setValue(value),
                () -> this.getOptions().add(VanaticOption.of(key, value)));
    }

    @Override
    public Optional<Option> getOption(String key) {
        for (final Option option : this.getOptions()) {
            if (option.getKey().equals(key)) {
                return Optional.of(option);
            }
        }
        return Optional.empty();
    }

    @Override
    public void flagPlayer(Player player, Value... values) {
        final int violations = this.getViolations(player);

        if (violations >= this.getBanAmount() &&
            this.getConfiguration().getSettings().isBanEnabled()) {
            // TODO: BAN PLAYER
            this.getViolationsMap().remove(player);
            return;
        }

        if (violations >= this.getKickAmount() &&
                this.getConfiguration().getSettings().isKickEnabled()) {
            // TODO: KICK PLAYER
            this.getViolationsMap().remove(player);
            return;
        }

        this.getViolationsMap().put(player, this.getViolations(player) + 1);
        this.getAPI().getNotificationAdapter().sendFlagNotification(player, this, values);

        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            if (this.getViolations(player).equals(0)) {
                this.getViolationsMap().remove(player);
            } else this.getViolationsMap().put(player, this.getViolations(player) - 1);
        }, 1, TimeUnit.SECONDS);
    }

    @Override
    public Integer getViolations(Player player) {
        return this.getViolationsMap().getOrDefault(player, 0);
    }

    @Override
    public Integer getKickAmount() {
        return this.kickAmount;
    }

    @Override
    public Integer getBanAmount() {
        return this.banAmount;
    }

    @Override
    public Integer getMaxViolation() {
        return this.maxViolation;
    }

    @Override
    public String getFunctionalName() {
        return this.getAbsoluteName() + "[" + this.getDescription().getType() + "]";
    }

    @Override
    public String getAbsoluteName() {
        return this.getDescription().getName() + this.getDescription().getType();
    }

    @Override
    public String getAbsolutePath() {
        return Paths.getPathCategory(this.getDescription().getCategory()).getPath() + this.getAbsoluteName() + ".json";
    }

    @Override
    public Vanatic getVanatic() {
        return VanaticPlugin.getService();
    }

    @Override
    public API getAPI() {
        return VanaticPlugin.getService().getAPI();
    }

    @Override
    public VanaticDetectionDescription getDescription() {
        return this.description;
    }

    @Override
    public VanaticDetectionConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override
    public List<VanaticOption> getOptions() {
        return this.options;
    }

    @Override
    public Map<Player, Integer> getViolationsMap() {
        return this.violationsMap;
    }

}
