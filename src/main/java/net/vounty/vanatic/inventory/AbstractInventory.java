package net.vounty.vanatic.inventory;

import net.vounty.vanatic.api.API;

public class AbstractInventory {

    private final API api;

    public AbstractInventory(API api) {
        this.api = api;
    }

    public API getAPI() {
        return this.api;
    }

}
