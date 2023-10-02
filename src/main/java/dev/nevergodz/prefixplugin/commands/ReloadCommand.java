package dev.nevergodz.prefixplugin.commands;

import dev.nevergodz.prefixplugin.PrefixPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final PrefixPlugin plugin;

    public ReloadCommand(PrefixPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("prefixreload")) {
            plugin.reloadConfig();
            sender.sendMessage("Конфиг перезагружен");
            return true;
        }
        return false;
    }
}
