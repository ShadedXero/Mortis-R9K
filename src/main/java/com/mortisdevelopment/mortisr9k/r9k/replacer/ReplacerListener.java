package com.mortisdevelopment.mortisr9k.r9k.replacer;

import com.mortisdevelopment.mortisr9k.utils.MessageUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.regex.Matcher;

public class ReplacerListener implements Listener {

    private final ReplacerManager replacerManager;

    public ReplacerListener(ReplacerManager replacerManager) {
        this.replacerManager = replacerManager;
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player player = e.getPlayer();
        if (!player.hasPermission("r9k.bypass")) {
            return;
        }
        String message = MessageUtils.color(e.message());
        Matcher blacklistMatcher = replacerManager.getSettings().getBlacklistPattern().matcher(message);
        if (blacklistMatcher.find()) {
            e.setCancelled(true);
            if (!replacerManager.getSettings().isNotifyOnBlacklist()) {
                return;
            }
            replacerManager.sendMessage(player, "MESSAGE_BLOCKED");
            for (Player target : Bukkit.getOnlinePlayers()) {
                if (target.hasPermission("r9k.notify")) {
                    target.sendMessage(replacerManager.getMessage("BLACKLIST_NOTIFICATION").replaceText(TextReplacementConfig.builder().match("%player_name%").replacement(player.getName()).build()));
                }
            }
            return;
        }

        Matcher matcher = replacerManager.getSettings().getPattern().matcher(message);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, replacerManager.getSettings().getReplacements().get(matcher.group().toLowerCase()));
        }
        matcher.appendTail(result);

        e.message(MessageUtils.getComponent(result.toString()));
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.isCancelled() || !replacerManager.getSettings().isModifyMsg()) {
            return;
        }
        Player player = e.getPlayer();
        if (!player.hasPermission("r9k.bypass")) {
            return;
        }
        String message = e.getMessage();
        Matcher blacklistMatcher = replacerManager.getSettings().getBlacklistPattern().matcher(message);
        if (blacklistMatcher.find()) {
            e.setCancelled(true);
            if (!replacerManager.getSettings().isNotifyOnBlacklist()) {
                return;
            }
            replacerManager.sendMessage(player, "MESSAGE_BLOCKED");
            for (Player target : Bukkit.getOnlinePlayers()) {
                if (target.hasPermission("r9k.notify")) {
                    target.sendMessage(replacerManager.getMessage("BLACKLIST_NOTIFICATION").replaceText(TextReplacementConfig.builder().match("%player_name%").replacement(player.getName()).build()));
                }
            }
            return;
        }

        Matcher matcher = replacerManager.getSettings().getPattern().matcher(message);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, replacerManager.getSettings().getReplacements().get(matcher.group().toLowerCase()));
        }
        matcher.appendTail(result);

        e.setMessage(result.toString());
    }
}
