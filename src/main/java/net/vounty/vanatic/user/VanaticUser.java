package net.vounty.vanatic.user;

import org.bukkit.entity.Player;

public class VanaticUser implements User {

    private final Player player;

    private Boolean isSprinting, isSneaking, isDigging, isEating, isBlocking, isRiding, isFalling, isInventoryOpen;
    private Long lastDigging, lastBlockPlaced, lastUsedEntity, lastSprinted, lastClicks, lastFalling, lastSneak, lastRide, lastInventory;

    public VanaticUser(Player player) {
        this.player = player;
    }

    @Override
    public void updateStatus(StatusType type, Boolean value) {
        switch (type) {
            case SPRINTING -> this.isSprinting = value;
            case SNEAKING -> this.isSneaking = value;
            case DIGGING -> this.isDigging = value;
            case EATING -> this.isEating = value;
            case BLOCKING -> this.isBlocking = value;
            case RIDING -> this.isRiding = value;
            case FALL -> this.isFalling = value;
            case INVENTORY -> this.isInventoryOpen = value;
        }
    }

    @Override
    public void updateLast(LastType type) {
        final long current = System.nanoTime();
        switch (type) {
            case DIGGING -> this.lastDigging = current;
            case BLOCK_PLACED -> this.lastBlockPlaced = current;
            case USED_ENTITY -> this.lastUsedEntity = current;
            case SPRINTED -> this.lastSprinted = current;
            case CLICKS -> this.lastClicks = current;
            case FALL -> this.lastFalling = current;
            case SNEAK -> this.lastSneak = current;
            case RIDE -> this.lastRide = current;
            case INVENTORY -> this.lastInventory = current;
        }
    }

    @Override
    public Boolean getStatus(StatusType type) {
        return switch (type) {
            case SPRINTING -> this.isSprinting;
            case SNEAKING -> this.isSneaking;
            case DIGGING -> this.isDigging;
            case EATING -> this.isEating;
            case BLOCKING -> this.isBlocking;
            case RIDING -> this.isRiding;
            case FALL -> this.isFalling;
            case INVENTORY -> this.isInventoryOpen;
        };
    }

    @Override
    public Long getLast(LastType type) {
        return switch (type) {
            case DIGGING -> this.lastDigging;
            case BLOCK_PLACED -> this.lastBlockPlaced;
            case USED_ENTITY -> this.lastUsedEntity;
            case SPRINTED -> this.lastSprinted;
            case CLICKS -> this.lastClicks;
            case FALL -> this.lastFalling;
            case SNEAK -> this.lastSneak;
            case RIDE -> this.lastRide;
            case INVENTORY -> this.lastInventory;
        };
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    public enum LastType {DIGGING, BLOCK_PLACED, USED_ENTITY, SPRINTED, CLICKS, FALL, SNEAK, RIDE, INVENTORY}
    public enum StatusType {SPRINTING, SNEAKING, DIGGING, EATING, BLOCKING, RIDING, FALL, INVENTORY}

}