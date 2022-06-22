package net.vounty.vanatic.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface VanaticInventory {

    void openInventory(Player player);
    void updateInventory(Player player, Inventory inventory);

}
