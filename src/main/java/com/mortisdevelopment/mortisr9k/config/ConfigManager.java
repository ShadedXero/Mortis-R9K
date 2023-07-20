package com.mortisdevelopment.mortisr9k.config;

import com.mortisdevelopment.mortisr9k.manager.Manager;

public class ConfigManager {

    private final Manager manager;
    private final MainConfig mainConfig;

    public ConfigManager(Manager manager) {
        this.manager = manager;
        mainConfig = new MainConfig(this);
    }

    public Manager getManager() {
        return manager;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }
}
