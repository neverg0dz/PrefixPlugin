package dev.nevergodz.prefixplugin.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.nevergodz.prefixplugin.managers.PrefixManager;

public class PrefixCommand implements CommandExecutor {

    private final PrefixManager prefixManager;

    public PrefixCommand(PrefixManager prefixManager) {
        this.prefixManager = prefixManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эту команду могут использовать только игроки.");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("prefix")) {
            if (args.length == 0) {
                player.sendMessage("Используйте:");
                player.sendMessage("/prefix list - для просмотра ваших префиксов.");
                player.sendMessage("/prefix add <ник/UUID> <префикс> - для добавления префикса.");
                player.sendMessage("/prefix remove <ник/UUID> <префикс> - для удаления префикса.");
            } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                prefixManager.listPrefixes(player); // Обработка команды /prefix list
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("add")) {
                    String targetName = args[1];
                    String prefixId = args[2];
                    prefixManager.addPrefix(player, targetName, prefixId);
                } else if (args[0].equalsIgnoreCase("remove")) {
                    String targetName = args[1];
                    String prefixId = args[2];
                    prefixManager.removePrefix(player, targetName, prefixId);
                } else {
                    player.sendMessage("Используйте:");
                    player.sendMessage("/prefix list - для просмотра ваших префиксов.");
                    player.sendMessage("/prefix add <ник/UUID> <префикс> - для добавления префикса.");
                    player.sendMessage("/prefix remove <ник/UUID> <префикс> - для удаления префикса.");
                }
            }
            return true;
        }
        return false;
    }
}
