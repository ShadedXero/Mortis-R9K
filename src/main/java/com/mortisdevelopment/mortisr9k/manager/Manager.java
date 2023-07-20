package com.mortisdevelopment.mortisr9k.manager;

import com.mortisdevelopment.mortisr9k.MortisR9K;
import com.mortisdevelopment.mortisr9k.config.ConfigManager;
import com.mortisdevelopment.mortisr9k.data.DataManager;
import com.mortisdevelopment.mortisr9k.r9k.originality.OriginalManager;
import com.mortisdevelopment.mortisr9k.r9k.replacer.ReplacerManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class Manager {

    private final MortisR9K plugin = MortisR9K.getInstance();
    private ReplacerManager replacerManager;
    private OriginalManager originalManager;
    private DataManager dataManager;
    private ConfigManager configManager;

    public Manager() {
        this.configManager = new ConfigManager(this);
        plugin.getServer().getPluginCommand("r9k").setExecutor(new R9KCommand(this));
    }

    public void reload() {
        HandlerList.unregisterAll(plugin);
        Bukkit.getScheduler().cancelTasks(plugin);
        setConfigManager(new ConfigManager(this));
    }

    public ReplacerManager getReplacerManager() {
        return replacerManager;
    }

    public void setReplacerManager(ReplacerManager replacerManager) {
        this.replacerManager = replacerManager;
    }

    public OriginalManager getOriginalManager() {
        return originalManager;
    }

    public void setOriginalManager(OriginalManager originalManager) {
        this.originalManager = originalManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }
}
