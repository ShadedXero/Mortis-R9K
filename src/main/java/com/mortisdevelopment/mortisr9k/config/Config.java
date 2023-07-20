package com.mortisdevelopment.mortisr9k.config;

import com.mortisdevelopment.mortisr9k.MortisR9K;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public abstract class Config {

    private final MortisR9K plugin = MortisR9K.getInstance();
    private final String fileName;

    public Config(@NotNull String fileName) {
        this.fileName = fileName;
    }

    public abstract void loadConfig();

    public File saveConfig() {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, true);
        }
        return file;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public String getFileName() {
        return fileName;
    }
}
