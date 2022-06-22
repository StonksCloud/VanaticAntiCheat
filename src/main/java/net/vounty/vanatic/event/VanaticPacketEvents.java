package net.vounty.vanatic.event;

import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PlayerEjectEvent;
import io.github.retrooper.packetevents.event.impl.PlayerInjectEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.in.blockdig.WrappedPacketInBlockDig;
import io.github.retrooper.packetevents.packetwrappers.play.in.entityaction.WrappedPacketInEntityAction;
import net.vounty.vanatic.api.API;
import net.vounty.vanatic.user.User;
import net.vounty.vanatic.user.VanaticUser;

import java.util.Optional;

public class VanaticPacketEvents extends PacketListenerAbstract {

    private final API api;

    public VanaticPacketEvents(API api) {
        this.api = api;
    }

    @Override
    public void onPlayerInject(PlayerInjectEvent event) {
        this.getAPI().getUserAdapter().registerUser(event.getPlayer());
    }

    @Override
    public void onPlayerEject(PlayerEjectEvent event) {
        this.getAPI().getUserAdapter().unregisterUser(event.getPlayer());
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        final Optional<User> optionalUser = this.getAPI().getUserAdapter().getUser(event.getPlayer());
        final byte packetId = event.getPacketId();

        if (optionalUser.isEmpty())
            return;

        final User user = optionalUser.get();

        if (packetId == PacketType.Play.Client.ENTITY_ACTION) {
            final WrappedPacketInEntityAction packet = new WrappedPacketInEntityAction(event.getNMSPacket());

            switch (packet.getAction()) {
                case START_SPRINTING, STOP_SPRINTING -> {
                    if (packet.getAction().equals(WrappedPacketInEntityAction.PlayerAction.STOP_SPRINTING)) user.updateLast(VanaticUser.LastType.SPRINTED);
                    user.updateStatus(VanaticUser.StatusType.SPRINTING, packet.getAction().equals(WrappedPacketInEntityAction.PlayerAction.START_SPRINTING));
                }
                case START_SNEAKING, STOP_SNEAKING -> {
                    if (packet.getAction().equals(WrappedPacketInEntityAction.PlayerAction.STOP_SNEAKING)) user.updateLast(VanaticUser.LastType.SNEAK);
                    user.updateStatus(VanaticUser.StatusType.SNEAKING, packet.getAction().equals(WrappedPacketInEntityAction.PlayerAction.STOP_SNEAKING));
                }
                case START_RIDING_JUMP, STOP_RIDING_JUMP -> {
                    if (packet.getAction().equals(WrappedPacketInEntityAction.PlayerAction.STOP_RIDING_JUMP)) user.updateLast(VanaticUser.LastType.RIDE);
                    user.updateStatus(VanaticUser.StatusType.RIDING, packet.getAction().equals(WrappedPacketInEntityAction.PlayerAction.STOP_RIDING_JUMP));
                }
                case START_FALL_FLYING -> {
                    user.updateStatus(VanaticUser.StatusType.FALL, true);
                }
                case OPEN_INVENTORY -> {
                    user.updateLast(VanaticUser.LastType.INVENTORY);
                    user.updateStatus(VanaticUser.StatusType.INVENTORY, true);
                }
            }
            return;

        }

        if (packetId == PacketType.Play.Client.USE_ENTITY) {
            user.updateLast(VanaticUser.LastType.USED_ENTITY);
            return;
        }

        if (packetId == PacketType.Play.Client.BLOCK_DIG) {
            final WrappedPacketInBlockDig packet = new WrappedPacketInBlockDig(event.getNMSPacket());

            switch (packet.getDigType()) {
                case START_DESTROY_BLOCK, STOP_DESTROY_BLOCK -> {
                    if (packet.getDigType().equals(WrappedPacketInBlockDig.PlayerDigType.STOP_DESTROY_BLOCK)) user.updateLast(VanaticUser.LastType.DIGGING);
                    user.updateStatus(VanaticUser.StatusType.DIGGING, packet.getDigType().equals(WrappedPacketInBlockDig.PlayerDigType.STOP_DESTROY_BLOCK));
                }
                case ABORT_DESTROY_BLOCK -> {
                    user.updateLast(VanaticUser.LastType.DIGGING);
                    user.updateStatus(VanaticUser.StatusType.DIGGING, false);
                }
                case RELEASE_USE_ITEM -> {
                    user.updateStatus(VanaticUser.StatusType.EATING, false);
                    user.updateStatus(VanaticUser.StatusType.BLOCKING, false);
                }
            }
            return;
        }

        if (packetId == PacketType.Play.Client.BLOCK_PLACE) {
            if (user.getPlayer().getItemInHand().getType().isEdible())
                user.updateStatus(VanaticUser.StatusType.EATING, true);
            return;
        }

        if (packetId == PacketType.Play.Client.ARM_ANIMATION) {
            user.updateStatus(VanaticUser.StatusType.BLOCKING, user.getPlayer().isBlocking());
        }

    }

    public API getAPI() {
        return this.api;
    }

}
