package dev.nevergodz.prefixplugin.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.nevergodz.prefixplugin.manager.PrefixManager;

public class PrefixCommand implements CommandExecutor {

    String infoMessage() {
        return "Используйте:\n" +
                "/prefix list - для просмотра ваших префиксов.\n" +
                "/prefix add <ник/UUID> <префикс> - для добавления префикса.\n" +
                "/prefix remove <ник/UUID> <префикс> - для удаления префикса.\n" +
                "/prefix reload - для перезагрузки конфигурации.";
    }

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
                infoMessage();
            } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                prefixManager.listPrefixes(player);
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
                    infoMessage();
                }
            } else {
                infoMessage();
            }
            return true;
        }
        return false;
    }
}
