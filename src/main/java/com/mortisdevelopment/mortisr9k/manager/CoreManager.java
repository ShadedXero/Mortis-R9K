package com.mortisdevelopment.mortisr9k.manager;

import com.mortisdevelopment.mortisr9k.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class CoreManager {

    private final HashMap<String, Component> messageById;

    public CoreManager() {
        this.messageById = new HashMap<>();
    }

    public CoreManager(ConfigurationSection section) {
        this.messageById = new HashMap<>();
        loadMessages(section);
    }

    public void loadMessages(ConfigurationSection section) {
        if (section == null) {
            return;
        }
        for (String key : section.getKeys(false)) {
            String id = key.replace("-", "_").toUpperCase();
            String message = section.getString(key);
            if (message == null) {
                continue;
            }
            messageById.put(id, MessageUtils.getComponent(message));
        }
    }

    public void addMessage(String id, Component message) {
        messageById.put(id, message);
    }

    public Component getMessage(String id) {
        return messageById.get(id);
    }

    public void sendMessage(Player player, String id) {
        Component message = getMessage(id);
        if (message == null) {
            return;
        }
        player.sendMessage(message);
    }

    public void sendMessage(CommandSender sender, String id) {
        Component message = getMessage(id);
        if (message == null) {
            return;
        }
        sender.sendMessage(message);
    }

    public void sendMessage(UUID uuid, String id) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return;
        }
        Component message = getMessage(id);
        if (message == null) {
            return;
        }
        player.sendMessage(message);
    }

    public void sendMessage(String id) {
        Component message = getMessage(id);
        if (message == null) {
            return;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public static Map<String, Component> getMessageById(ConfigurationSection section) {
        Map<String, Component> messageById = new HashMap<>();
        if (section == null) {
            return messageById;
        }
        for (String key : section.getKeys(false)) {
            String id = key.replace("-", "_").toUpperCase();
            String message = section.getString(key);
            if (message == null) {
                continue;
            }
            messageById.put(id, MessageUtils.getComponent(message));
        }
        return messageById;
    }
    public static Map<String, String> getRawMessageById(ConfigurationSection section) {
        Map<String, String> messageById = new HashMap<>();
        if (section == null) {
            return messageById;
        }
        for (String key : section.getKeys(false)) {
            String id = key.replace("-", "_").toUpperCase();
            String message = section.getString(key);
            if (message == null) {
                continue;
            }
            messageById.put(id, MessageUtils.color(message));
        }
        return messageById;
    }

    public HashMap<String, Component> getMessageById() {
        return messageById;
    }
}
