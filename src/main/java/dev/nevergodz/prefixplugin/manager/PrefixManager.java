package dev.nevergodz.prefixplugin.manager;

import dev.nevergodz.prefixplugin.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PrefixManager {
    private final Map<UUID, String> playerPrefixes = new HashMap<>();
    private final FileConfiguration config;
    private final DatabaseImpl DatabaseImpl;


    public PrefixManager(FileConfiguration config, DatabaseImpl Databaseimpl) {
        this.config = config;
        this.DatabaseImpl = Databaseimpl;
    }

    public void setPrefix(UUID uuid, String prefixId) {
        playerPrefixes.put(uuid, prefixId);
    }

    public String getPrefix(UUID playerUUID) {
        return playerPrefixes.get(playerUUID);
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

    public Player getPlayerByNameOrUUID(String nameOrUUID) {
        return Bukkit.getPlayer(nameOrUUID);
    }

    public void addPrefix(Player sender, String targetName, String name) {
        String prefixId = findPrefixIdByName(name);

        if (prefixId == null) {
            sender.sendMessage("Префикс " + name + " не найден.");
            return;
        }

        Player target = getPlayerByNameOrUUID(targetName);

        if (target == null) {
            sender.sendMessage("Игрок " + targetName + " не найден.");
            return;
        }

        String displayName = config.getString("prefixes." + prefixId + ".displayName");

        setPrefix(target.getUniqueId(), prefixId);

        sender.sendMessage("Префикс " + name + " установлен для " + target.getName());
    }

    public void removePrefix(Player sender, String targetName, String prefixId) {
        Player target = getPlayerByNameOrUUID(targetName);

        if (target == null) {
            sender.sendMessage("Игрок " + targetName + " не найден.");
            return;
        }

        String playerPrefix = getPrefix(target.getUniqueId());

        if (playerPrefix == null || !playerPrefix.equals(prefixId)) {
            sender.sendMessage("Игрок " + target.getName() + " не имеет префикса: " + prefixId);
            return;
        }

        playerPrefixes.remove(target.getUniqueId());

        sender.sendMessage("Префикс: " + prefixId + " удален у " + target.getName());
    }
}
