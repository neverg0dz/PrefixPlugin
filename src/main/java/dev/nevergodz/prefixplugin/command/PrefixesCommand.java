package dev.nevergodz.prefixplugin.command;

import dev.nevergodz.prefixplugin.manager.PrefixManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrefixesCommand implements CommandExecutor {

    private final PrefixManager prefixManager;

    public PrefixesCommand(PrefixManager prefixManager) {
        this.prefixManager = prefixManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эту команду могут использовать только игроки.");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("prefixes")) {
            if (args.length == 0) {
                prefixManager.listPrefixes(player);
            } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
                String prefixId = args[1];
                // Устанавливаем выбранный префикс
                prefixManager.addPrefix(player, player.getName(), prefixId);
            } else {
                sender.sendMessage("Неверный формат команды. Используйте /prefixes или /prefixes set <id>.");
            }
            return true;
        }
        return false;
    }
}