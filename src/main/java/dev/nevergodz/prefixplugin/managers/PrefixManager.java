package dev.nevergodz.prefixplugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PrefixManager {

    private final JavaPlugin plugin;
    private final FileConfiguration config;
    private final Map<Player, String> playerPrefixes = new HashMap<>();

    public PrefixManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public void setPlayerListName(Player player, String displayName) {
        player.setPlayerListName(displayName);
    }

    public String findPrefixIdByName(String name) {
        Set<String> prefixIds = config.getConfigurationSection("prefixes").getKeys(false);

        for (String prefixId : prefixIds) {
            String prefixName = config.getString("prefixes." + prefixId + ".name");
            if (prefixName != null && prefixName.equalsIgnoreCase(name)) {
                return prefixId;
            }
        }

        return null;
    }

    public void listPrefixes(Player player) {
        player.sendMessage("Все существующие префиксы:");

        Set<String> prefixIds = config.getConfigurationSection("prefixes").getKeys(false);

        if (prefixIds.isEmpty()) {
            player.sendMessage("Нет доступных префиксов.");
        } else {
            for (String prefixId : prefixIds) {
                String prefixName = config.getString("prefixes." + prefixId + ".name");
                String prefixDisplayName = config.getString("prefixes." + prefixId + ".displayName");
                player.sendMessage(prefixId + ": " + prefixName + " (" + prefixDisplayName + ")");
            }
        }
    }

    public void addPrefix(Player sender, String targetName, String name) {
        String prefixId = findPrefixIdByName(name);

        if (prefixId == null) {
            sender.sendMessage("Префикс " + name + " не найден.");
            return;
        }

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
        playerPrefixes.put(target, currentPrefixes); // Добавьте эту строку

        String displayName = config.getString("prefixes." + prefixId + ".displayName");
        target.setPlayerListName(target.getName() + " " + displayName);

        sender.sendMessage("Префикс " + name + " установлен для " + target.getName());
    }


    public void removePrefix(Player sender, String targetName, String prefixId) {
        Player target = plugin.getServer().getPlayer(targetName);

        if (target == null) {
            sender.sendMessage("Игрок " + targetName + " не найден.");
            return;
        }

        String currentPrefixes = playerPrefixes.getOrDefault(target, "");
        String[] prefixes = currentPrefixes.split(",");

        boolean removed = false;
        StringBuilder newPrefixes = new StringBuilder();
        for (String playerPrefix : prefixes) {
            if (!playerPrefix.equals(prefixId)) {
                if (newPrefixes.length() > 0) {
                    newPrefixes.append(",");
                }
                newPrefixes.append(playerPrefix);
            } else {
                removed = true;
            }
        }

        if (!removed) {
            sender.sendMessage("Игрок " + target.getName() + " не имеет префикса: " + prefixId);
        } else {
            // Обновить сообщение в чате с новыми префиксами
            setPlayerListName(target, target.getName() + " " + newPrefixes.toString());

            sender.sendMessage("Префикс: " + prefixId + " удален у " + target.getName());
        }

        playerPrefixes.put(target, newPrefixes.toString());
        setPlayerListName(target, target.getName());
    }
}