package dev.nevergodz.prefixplugin;

import dev.nevergodz.prefixplugin.command.PrefixCommand;
import dev.nevergodz.prefixplugin.command.PrefixesCommand;
import dev.nevergodz.prefixplugin.listener.PlayerJoinListener;
import dev.nevergodz.prefixplugin.manager.PrefixManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    private PrefixManager prefixManager;


    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();

        prefixManager = new PrefixManager(config);

        getCommand("prefix").setExecutor(new PrefixCommand(prefixManager));
        getCommand("prefixes").setExecutor(new PrefixesCommand(prefixManager));

        registerListeners(
                new PlayerJoinListener(prefixManager)
        );
    }

    @Override
    public void onDisable() {

    }

    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    public PrefixManager getPrefixManager() {
        return prefixManager;
    }
}
