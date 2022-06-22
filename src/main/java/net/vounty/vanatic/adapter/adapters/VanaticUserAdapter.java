package net.vounty.vanatic.adapter.adapters;

import net.vounty.vanatic.adapter.VanaticAdapter;
import net.vounty.vanatic.api.API;
import net.vounty.vanatic.user.User;
import net.vounty.vanatic.user.VanaticUser;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VanaticUserAdapter extends VanaticAdapter implements UserAdapter {

    private final List<User> users;

    public VanaticUserAdapter(API api) {
        super(api);
        this.users = new LinkedList<>();
    }

    @Override
    public void registerUser(Player player) {
        final Optional<User> optionalUser = this.getUser(player);
        if (optionalUser.isEmpty()) this.getUsers().add(new VanaticUser(player));
    }

    @Override
    public void unregisterUser(Player player) {
        final Optional<User> optionalUser = this.getUser(player);
        optionalUser.ifPresent(user -> this.getUsers().remove(user));
    }

    @Override
    public Optional<User> getUser(Player player) {
        for (final User user : this.getUsers()) {
            if (user.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUser(UUID uniqueId) {
        for (final User user : this.getUsers()) {
            if (user.getPlayer().getUniqueId().equals(uniqueId)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public User getUserUnsafe(Player player) {
        for (final User user : this.getUsers()) {
            if (user.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserUnsafe(UUID uniqueId) {
        for (final User user : this.getUsers()) {
            if (user.getPlayer().getUniqueId().equals(uniqueId)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        return this.users;
    }

}
