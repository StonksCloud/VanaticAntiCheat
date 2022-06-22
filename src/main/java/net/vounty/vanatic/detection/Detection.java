package net.vounty.vanatic.detection;

import io.github.retrooper.packetevents.packetwrappers.NMSPacket;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import net.vounty.vanatic.Vanatic;
import net.vounty.vanatic.api.API;
import net.vounty.vanatic.detection.option.Option;
import net.vounty.vanatic.detection.option.VanaticOption;
import net.vounty.vanatic.detection.value.Value;
import net.vounty.vanatic.user.User;
import net.vounty.vanatic.utils.enums.ProtocolMode;
import net.vounty.vanatic.utils.enums.ProtocolType;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Detection {

    void onInitialize();
    void onTerminate();

    void onMount();
    void onShutdown();

    Boolean onPacket(User user, NMSPacket nmsPacket, WrappedPacket wrappedPacket, ProtocolMode mode, ProtocolType type);

    Boolean isBlockedListen(Player player);

    void debug(Player player, Value... values);

    void flagPlayer(Player player, Value... values);
    Integer getViolations(Player player);

    void saveConfiguration(Boolean override);
    void resetConfiguration();

    void registerOption(String key, Object value);
    Optional<Option> getOption(String key);

    Integer getKickAmount();
    Integer getBanAmount();
    Integer getMaxViolation();

    String getFunctionalName();
    String getAbsoluteName();
    String getAbsolutePath();

    Vanatic getVanatic();
    API getAPI();

    VanaticDetectionDescription getDescription();
    VanaticDetectionConfiguration getConfiguration();

    List<VanaticOption> getOptions();
    Map<Player, Integer> getViolationsMap();

}