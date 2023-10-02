package dev.nevergodz.prefixplugin;

import dev.nevergodz.prefixplugin.commands.PrefixCommand;
import dev.nevergodz.prefixplugin.commands.ReloadCommand;
import dev.nevergodz.prefixplugin.managers.PrefixManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class PrefixPlugin extends JavaPlugin {

    private PrefixManager prefixManager;


    @Override
    public void onEnable() {
        prefixManager = new PrefixManager(this);
        getCommand("prefix").setExecutor(new PrefixCommand(prefixManager));
        getCommand("prefixreload").setExecutor(new ReloadCommand(this));

        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();

        String prefixId = "1"; // Здесь определите нужный вам ID префикса
        String displayName = config.getString("prefixes." + prefixId + ".displayName");

    }

    @Override
    public void onDisable() {

    }
    public PrefixManager getPrefixManager() {
        return prefixManager;
    }
}

