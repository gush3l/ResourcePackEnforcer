package me.gushel.resourcepackenforcer;

import me.gushel.resourcepackenforcer.commands.MainCommand;
import me.gushel.resourcepackenforcer.listeners.PlayerJoin;
import me.gushel.resourcepackenforcer.listeners.PlayerResourcePackStatus;
import me.gushel.resourcepackenforcer.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class ResourcePackEnforcer extends JavaPlugin {

    private static ResourcePackEnforcer plugin;
    private PlayerManager playerManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        Logger log = this.getLogger();
        FileConfiguration config = this.getConfig();

        plugin = this;
        Objects.requireNonNull(this.getCommand("resourcepackenforcer")).setExecutor(new MainCommand());

        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerResourcePackStatus(), this);

        playerManager = new PlayerManager();
        playerManager.task();

        new Placeholders().register();

        if (Objects.equals(config.getString("settings.resourcepack"), "NO_LINK")){
            log.warning("You didn't set any resourcepack link in the config file! Please do that and reload the plugin in order to send a resourcepack to players!");
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        playerManager.clear();
    }

    public static ResourcePackEnforcer getInstance(){
        return plugin;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
