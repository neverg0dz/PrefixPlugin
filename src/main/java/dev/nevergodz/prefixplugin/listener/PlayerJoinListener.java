package dev.nevergodz.prefixplugin.listener;

import dev.nevergodz.prefixplugin.manager.PrefixManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {
    private final PrefixManager prefixManager;

    public PlayerJoinListener(PrefixManager prefixManager) {
        this.prefixManager = prefixManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();

        String prefix = prefixManager.getPrefix(playerUUID);

        if (prefix != null) {
            prefixManager.setPlayerListName(event.getPlayer(), prefix);
        }
    }
}
