package dev.nevergodz.prefixplugin;

import dev.nevergodz.prefixplugin.commands.PrefixCommand;
import dev.nevergodz.prefixplugin.managers.PrefixManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PrefixPlugin extends JavaPlugin {

    private PrefixManager prefixManager;


    @Override
    public void onEnable() {
        prefixManager = new PrefixManager(this);
        getCommand("prefix").setExecutor(new PrefixCommand(prefixManager));

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {

    }


    public PrefixManager getPrefixManager() {
        return prefixManager;
    }

}
