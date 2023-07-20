package com.mortisdevelopment.mortisr9k;

import com.mortisdevelopment.mortisr9k.manager.Manager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MortisR9K extends JavaPlugin {

    private static MortisR9K Instance;
    private Manager manager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Instance = this;
        manager = new Manager();
    }

    public static MortisR9K getInstance() {
        return Instance;
    }

    public Manager getManager() {
        return manager;
    }
}
