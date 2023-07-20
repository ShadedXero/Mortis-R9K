package com.mortisdevelopment.mortisr9k.manager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class R9KCommand implements TabExecutor {

    private final Manager manager;

    public R9KCommand(Manager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("r9k.reload")) {
                manager.getOriginalManager().sendMessage(sender, "NO_PERMISSION");
                return false;
            }
            manager.reload();
            manager.getOriginalManager().sendMessage(sender, "RELOAD");
            return true;
        }
        if (args[0].equalsIgnoreCase("purge")) {
            if (!sender.hasPermission("r9k.purge")) {
                manager.getOriginalManager().sendMessage(sender, "NO_PERMISSION");
                return false;
            }
            manager.getDataManager().clear();
            manager.getOriginalManager().sendMessage(sender, "PURGE");
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("reload");
            arguments.add("purge");
            return arguments;
        }
        return null;
    }
}
