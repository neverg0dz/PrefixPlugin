package dev.nevergodz.prefixplugin.managers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class PrefixManager {

    private final JavaPlugin plugin;
    private final FileConfiguration config;

    public PrefixManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public void setPlayerPrefix(Player player, String prefix) {
        // Установка суффикса в табе
        setTabSuffix(player, prefix);

        // Установка префикса над головой
        sendPrefixAboveHead(player, prefix);
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

    public void removePrefix(Player sender, Player target, String prefixId) {
        // Удаление префикса у игрока
        removePlayerPrefix(target);

        sender.sendMessage("Префикс с ID: " + prefixId + " успешно удален у " + target.getName());
    }

    private void setTabSuffix(Player player, String suffix) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager()
                .createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);

        // Устанавливаем имя команды (ник игрока)
        packet.getStrings().write(0, player.getName());
        // Устанавливаем суффикс для отображения в табе
        packet.getStrings().write(2, suffix);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void sendPrefixAboveHead(Player player, String prefix) {
        // Отправка пакета для отображения префикса над головой
        PacketContainer packet = ProtocolLibrary.getProtocolManager()
                .createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);

        // Устанавливаем уникальное имя команды (нужно для разных префиксов у разных игроков)
        packet.getStrings().write(0, player.getUniqueId().toString());
        // Устанавливаем название команды (для отображения префикса)
        packet.getStrings().write(2, prefix);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void removePlayerPrefix(Player player) {
        // Удаляем суффикс в табе
        setTabSuffix(player, "");

        // Отправляем пустой префикс над головой
        sendPrefixAboveHead(player, "");
    }
}
