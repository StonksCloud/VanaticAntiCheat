package net.vounty.vanatic.adapter;

import net.vounty.vanatic.api.API;

public class VanaticAdapter implements Adapter {

    private final API api;

    public VanaticAdapter(API api) {
        this.api = api;
    }

    public API getAPI() {
        return this.api;
    }

}