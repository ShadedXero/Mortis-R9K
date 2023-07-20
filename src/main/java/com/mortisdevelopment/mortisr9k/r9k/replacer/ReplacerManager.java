package com.mortisdevelopment.mortisr9k.r9k.replacer;

import com.mortisdevelopment.mortisr9k.MortisR9K;
import com.mortisdevelopment.mortisr9k.manager.CoreManager;

public class ReplacerManager extends CoreManager {

    private final ReplacerSettings settings;

    public ReplacerManager(ReplacerSettings settings) {
        this.settings = settings;
        MortisR9K plugin = MortisR9K.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new ReplacerListener(this), plugin);
    }

    public ReplacerSettings getSettings() {
        return settings;
    }
}
