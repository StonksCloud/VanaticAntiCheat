package net.vounty.vanatic.adapter.adapters;

import net.vounty.vanatic.user.User;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAdapter {

    void registerUser(Player player);
    void unregisterUser(Player player);

    Optional<User> getUser(Player player);
    Optional<User> getUser(UUID uniqueId);
    User getUserUnsafe(Player player);
    User getUserUnsafe(UUID uniqueId);

    List<User> getUsers();

}