package dev.nevergodz.prefixplugin;

import dev.nevergodz.prefixplugin.command.PrefixCommand;
import dev.nevergodz.prefixplugin.command.PrefixesCommand;
import dev.nevergodz.prefixplugin.listener.PlayerJoinListener;
import dev.nevergodz.prefixplugin.manager.DatabaseImpl;
import dev.nevergodz.prefixplugin.manager.DatabaseImpl;
import dev.nevergodz.prefixplugin.manager.PrefixManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class PrefixPlugin extends JavaPlugin {

    private PrefixManager prefixManager;
    private DatabaseImpl databaseManager;

    @Override
    public void onEnable() {
        // Загружаем конфигурацию
        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();

        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/neverdb";
        String username = "never";
        String password = ")(DFUS()*FUSDJF*(OSE*(F)UJFG)(*DFUS(*FYH(*SYHG(8hfshfg98shf-";

        int colonIndex = jdbcUrl.indexOf(":");
        int slashIndex = jdbcUrl.indexOf("/");
        String host = jdbcUrl.substring(colonIndex + 3, slashIndex);
        int lastColonIndex = jdbcUrl.lastIndexOf(":");
        int port = Integer.parseInt(jdbcUrl.substring(lastColonIndex + 1, slashIndex));
        String database = jdbcUrl.substring(slashIndex + 1);

        DatabaseImpl databaseManager = new DatabaseImpl(host, port, database, username, password);



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
