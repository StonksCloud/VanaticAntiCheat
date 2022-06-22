package net.vounty.vanatic.user;

import org.bukkit.entity.Player;

public interface User {

    void updateStatus(VanaticUser.StatusType type, Boolean value);
    void updateLast(VanaticUser.LastType type);

    Boolean getStatus(VanaticUser.StatusType type);
    Long getLast(VanaticUser.LastType type);

    Player getPlayer();

}