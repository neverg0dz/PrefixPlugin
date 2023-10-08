package dev.nevergodz.prefixplugin;

import dev.nevergodz.prefixplugin.command.PrefixCommand;
import dev.nevergodz.prefixplugin.command.PrefixesCommand;
import dev.nevergodz.prefixplugin.listener.PlayerJoinListener;
import dev.nevergodz.prefixplugin.manager.DatabaseManager;
import dev.nevergodz.prefixplugin.manager.PrefixManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class PrefixPlugin extends JavaPlugin {

    private PrefixManager prefixManager;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        // Загружаем конфигурацию
        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();

        String jdbcUrl = config.getString("database.url");
        String username = config.getString("database.username");
        String password = config.getString("database.password");

        databaseManager = new DatabaseManager(jdbcUrl, username, password);


        prefixManager = new PrefixManager(config, databaseManager);

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
