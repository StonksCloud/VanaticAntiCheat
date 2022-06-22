package net.vounty.vanatic;

import io.github.retrooper.packetevents.PacketEvents;
import net.vounty.vanatic.api.API;
import net.vounty.vanatic.detection.VanaticDetection;
import org.bukkit.event.Listener;

public interface Vanatic {

    void disablePlugin();
    void registerEvent(Listener listener);
    void unregisterEvent(Listener listener);

    API getAPI();
    PacketEvents getPacketEvents();

}