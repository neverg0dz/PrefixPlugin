package dev.nevergodz.prefixplugin.managers;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class PrefixManager {

    private final JavaPlugin plugin;
    private final Map<Player, String> playerPrefixes = new HashMap<>();

    public PrefixManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void listPrefixes(Player player) {
        player.sendMessage("Ваши префиксы:");
        if (playerPrefixes.containsKey(player)) {
            for (String prefix : playerPrefixes.get(player).split(",")) {
                player.sendMessage((prefix));
            }
        } else {
            player.sendMessage("У вас нет префиксов.");
        }
    }

    public void addPrefix(Player sender, String targetName, String prefixId) {
        Player target = plugin.getServer().getPlayer(targetName);

        if (target == null) {
            sender.sendMessage("Игрок " + targetName + " не найден.");
            return;
        }

        String currentPrefixes = playerPrefixes.getOrDefault(target, "");
        if (!currentPrefixes.isEmpty()) {
            currentPrefixes += ",";
        }
        currentPrefixes += prefixId;
        playerPrefixes.put(target, currentPrefixes);
        sender.sendMessage("Префикс успешно установлен: " + prefixId + " для " + target.getName());
    }

    public void removePrefix(Player sender, String targetName, String prefixId) {
        Player target = plugin.getServer().getPlayer(targetName);

        if (target == null) {
            sender.sendMessage("Игрок с именем " + targetName + " не найден.");
            return;
        }

        if (!playerPrefixes.containsKey(target)) {
            sender.sendMessage("Игрок " + target.getName() + " не имеет префиксов.");
            return;
        }

        String currentPrefixes = playerPrefixes.get(target);
        String[] prefixes = currentPrefixes.split(",");

        StringBuilder newPrefixes = new StringBuilder();
        boolean removed = false;
        for (String prefix : prefixes) {
            if (!prefix.equals(prefixId)) {
                if (newPrefixes.length() > 0) {
                    newPrefixes.append(",");
                }
                newPrefixes.append(prefix);
            } else {
                removed = true;
            }
        }

        if (!removed) {
            sender.sendMessage("Игрок " + target.getName() + " не имеет префикса с ID: " + prefixId);
            return;
        }

        playerPrefixes.put(target, newPrefixes.toString());
        sender.sendMessage("Префикс с ID: " + prefixId + " успешно удален у " + target.getName());
    }


}
