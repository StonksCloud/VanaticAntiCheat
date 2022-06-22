package net.vounty.vanatic;

import io.github.retrooper.packetevents.PacketEvents;
import net.vounty.vanatic.api.API;
import net.vounty.vanatic.api.VanaticAPI;
import net.vounty.vanatic.command.VanaticCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class VanaticPlugin extends JavaPlugin implements Vanatic {

    private static VanaticPlugin service;
    private API api;
    private PacketEvents packetEvents;

    @Override
    public void onEnable() {

        if (!this.existPacketEvents()) {
            System.out.println("Can't find Plugin PacketEvents.");
            this.disablePlugin();
            return;
        }

        VanaticPlugin.service = this;
        this.packetEvents = PacketEvents.create(this);
        this.packetEvents.init();
        this.api = VanaticAPI.of(this);
        this.api.onMountVanatic();
        this.registerCommand();
    }

    @Override
    public void onDisable() {
        this.api.onShutdownVanatic();
        this.packetEvents.terminate();
        this.api = null;
        this.packetEvents = null;
        VanaticPlugin.service = null;
    }

    @Override
    public void registerEvent(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void unregisterEvent(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    private Boolean existPacketEvents() {
        return this.getServer().getPluginManager().getPlugin("PacketEvents") == null;
    }

    private void registerCommand() {
        final PluginCommand pluginCommand = this.getCommand("vanatic");
        if (pluginCommand == null)
            return;
        final VanaticCommand vanaticCommand = new VanaticCommand();
        pluginCommand.setExecutor(vanaticCommand);
        pluginCommand.setTabCompleter(vanaticCommand);
    }

    @Override
    public void disablePlugin() {
        this.getServer().getPluginManager().disablePlugin(this);
    }

    @Override
    public API getAPI() {
        return this.api;
    }

    @Override
    public PacketEvents getPacketEvents() {
        return this.packetEvents;
    }

    public static VanaticPlugin getService() {
        return VanaticPlugin.service;
    }

}
