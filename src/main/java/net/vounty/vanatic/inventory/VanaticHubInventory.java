package net.vounty.vanatic.inventory;

import net.vounty.vanatic.api.API;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class VanaticHubInventory extends AbstractInventory implements VanaticInventory {

    public VanaticHubInventory(API api) {
        super(api);
    }

    @Override
    public void openInventory(Player player) {
        final String title = this.getAPI().getMessageAdapter().format("{suffix}GUI", true);
        final Inventory inventory = Bukkit.createInventory(player, 5 * 9, title);



        player.openInventory(inventory);
    }

    @Override
    public void updateInventory(Player player, Inventory inventory) {}

}