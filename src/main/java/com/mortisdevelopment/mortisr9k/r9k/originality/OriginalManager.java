package com.mortisdevelopment.mortisr9k.r9k.originality;

import com.mortisdevelopment.mortisr9k.MortisR9K;
import com.mortisdevelopment.mortisr9k.data.DataManager;
import com.mortisdevelopment.mortisr9k.manager.CoreManager;

public class OriginalManager extends CoreManager {

    private final DataManager dataManager;
    private final OriginalSettings settings;

    public OriginalManager(DataManager dataManager, OriginalSettings settings) {
        this.dataManager = dataManager;
        this.settings = settings;
        MortisR9K plugin = MortisR9K.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new OriginalListener(this), plugin);
    }

    public String getCommand(String command) {
        for (OriginalCommand originalCommand : settings.getCommands()) {
            if (!originalCommand.isCommand(command)) {
                continue;
            }
            return originalCommand.getMessage(command);
        }
        return null;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public OriginalSettings getSettings() {
        return settings;
    }
}
