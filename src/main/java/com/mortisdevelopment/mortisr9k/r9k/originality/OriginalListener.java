package com.mortisdevelopment.mortisr9k.r9k.originality;

import com.mortisdevelopment.mortisr9k.MortisR9K;
import com.mortisdevelopment.mortisr9k.utils.MessageUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OriginalListener implements Listener {

    private final MortisR9K plugin = MortisR9K.getInstance();
    private final OriginalManager originalManager;

    public OriginalListener(OriginalManager originalManager) {
        this.originalManager = originalManager;
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        if (e.isCancelled() || !originalManager.getSettings().isEnabled()) {
            return;
        }
        Player player = e.getPlayer();
        if (player.hasPermission("r9k.bypass")) {
            return;
        }
        String message = MessageUtils.color(e.message());
        if (!originalManager.getDataManager().getOriginals().contains(message)) {
            originalManager.getDataManager().add(message);
            return;
        }
        e.setCancelled(true);
        originalManager.sendMessage(player, "UNORIGINAL_NOTIFICATION");
        for (String command : originalManager.getSettings().getCommandsOnUnOriginal()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player_name%", player.getName()));
                }
            }.runTask(plugin);
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.isCancelled() || !originalManager.getSettings().isEnabled() || !originalManager.getSettings().isModifyMsgOriginal()) {
            return;
        }
        Player player = e.getPlayer();
        if (player.hasPermission("r9k.bypass")) {
            return;
        }
        String message = originalManager.getCommand(e.getMessage());
        if (message == null) {
            return;
        }
        if (!originalManager.getDataManager().getOriginals().contains(message)) {
            originalManager.getDataManager().add(message);
            return;
        }
        e.setCancelled(true);
        originalManager.sendMessage(player, "UNORIGINAL_NOTIFICATION");
        for (String command : originalManager.getSettings().getCommandsOnUnOriginal()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player_name%", player.getName()));
                }
            }.runTask(plugin);
        }
    }
}
