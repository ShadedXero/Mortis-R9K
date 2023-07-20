package com.mortisdevelopment.mortisr9k.config;

import com.mortisdevelopment.mortisr9k.data.DataManager;
import com.mortisdevelopment.mortisr9k.data.H2Database;
import com.mortisdevelopment.mortisr9k.r9k.originality.OriginalCommand;
import com.mortisdevelopment.mortisr9k.r9k.originality.OriginalManager;
import com.mortisdevelopment.mortisr9k.r9k.originality.OriginalSettings;
import com.mortisdevelopment.mortisr9k.r9k.replacer.ReplacerManager;
import com.mortisdevelopment.mortisr9k.r9k.replacer.ReplacerSettings;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MainConfig extends Config {

    private final ConfigManager configManager;

    public MainConfig(ConfigManager configManager) {
        super("config.yml");
        this.configManager = configManager;
        loadConfig();
    }

    @Override
    public void loadConfig() {
        File file = saveConfig();
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ReplacerSettings replacerSettings = loadReplacerSettings(config);
        if (replacerSettings == null) {
            return;
        }
        configManager.getManager().setReplacerManager(new ReplacerManager(replacerSettings));
        configManager.getManager().getReplacerManager().loadMessages(config.getConfigurationSection("messages"));
        OriginalSettings originalSettings = loadOriginalSettings(config);
        if (originalSettings == null) {
            return;
        }
        H2Database database = loadDatabase(config);
        if (database == null) {
            return;
        }
        configManager.getManager().setDataManager(new DataManager(database));
        configManager.getManager().setOriginalManager(new OriginalManager(configManager.getManager().getDataManager(), originalSettings));
        configManager.getManager().getOriginalManager().loadMessages(config.getConfigurationSection("messages"));
    }

    private ReplacerSettings loadReplacerSettings(ConfigurationSection section) {
        if (section == null) {
            return null;
        }
        boolean caseSensitive = section.getBoolean("caseSensitive");
        boolean notifyOnBlacklist = section.getBoolean("notifyOnBlacklist");
        boolean modifyMsg = section.getBoolean("modifyMsg");
        boolean precisionReplace = section.getBoolean("precisionReplace");
        ConfigurationSection replacementSection = section.getConfigurationSection("replacements");
        if (replacementSection == null) {
            return null;
        }
        Map<String, String> replacements = new HashMap<>();
        StringBuilder patternBuilder = new StringBuilder();
        for (String key : replacementSection.getKeys(false)) {
            if (patternBuilder.length() > 0) {
                patternBuilder.append("|");
            }
            patternBuilder.append(precisionReplace ? "\\b" + Pattern.quote(key) + "\\b" : Pattern.quote(key));
            replacements.put(caseSensitive ? key : key.toLowerCase(), replacementSection.getString(key));
        }
        Pattern pattern = Pattern.compile(patternBuilder.toString(), (caseSensitive ? 0 : Pattern.CASE_INSENSITIVE));
        Pattern blacklistPattern = null;
        List<String> blacklist = section.getStringList("blacklist");
        if (!blacklist.isEmpty()) {
            StringBuilder blacklistPatternBuilder = new StringBuilder();
            for (String word : blacklist) {
                if (blacklistPatternBuilder.length() > 0) {
                    blacklistPatternBuilder.append("|");
                }
                blacklistPatternBuilder.append("\\b").append(Pattern.quote(word)).append("\\b");
            }
            blacklistPattern = Pattern.compile(blacklistPatternBuilder.toString(), (caseSensitive ? 0 : Pattern.CASE_INSENSITIVE));
        }
        return new ReplacerSettings(notifyOnBlacklist, modifyMsg, replacements, pattern, blacklistPattern);
    }

    private OriginalSettings loadOriginalSettings(ConfigurationSection section) {
        if (section == null) {
            return null;
        }
        boolean enabled = section.getBoolean("originality");
        boolean modifyMsgOriginal = section.getBoolean("modifyMsgOriginal");
        List<OriginalCommand> commands = new ArrayList<>();
        ConfigurationSection commandSection = section.getConfigurationSection("commands");
        if (commandSection != null) {
            for (String key : commandSection.getKeys(false)) {
                ConfigurationSection keySection = commandSection.getConfigurationSection(key);
                if (keySection == null) {
                    continue;
                }
                String command = keySection.getString("command");
                if (command == null) {
                    continue;
                }
                int arguments = keySection.getInt("arguments");
                OriginalCommand originalCommand = new OriginalCommand(command, arguments);
                commands.add(originalCommand);
            }
        }
        List<String> commandsOnUnOriginal = section.getStringList("commands-on-unoriginal");
        return new OriginalSettings(enabled, modifyMsgOriginal, commands, commandsOnUnOriginal);
    }

    private H2Database loadDatabase(ConfigurationSection section) {
        if (section == null) {
            return null;
        }
        ConfigurationSection databaseSection = section.getConfigurationSection("database");
        if (databaseSection == null) {
            return null;
        }
        String fileName = databaseSection.getString("file");
        if (fileName == null) {
            return null;
        }
        File file = new File(getPlugin().getDataFolder(), fileName);
        String username = databaseSection.getString("username");
        String password = databaseSection.getString("password");
        return new H2Database(file, username, password);
    }
}
