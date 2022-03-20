package me.gushel.resourcepackenforcer.commands;

import me.gushel.resourcepackenforcer.ResourcePackEnforcer;
import me.gushel.resourcepackenforcer.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.logging.Logger;

public class Send {

    public static void onCommand(Player player){
        FileConfiguration config = ResourcePackEnforcer.getInstance().getConfig();
        Logger log = ResourcePackEnforcer.getInstance().getLogger();
        if (ResourcePackEnforcer.getInstance().getPlayerManager().getPlayer(player.getUniqueId()) != null) ResourcePackEnforcer.getInstance().getPlayerManager().removePlayer(player.getUniqueId());
        if (Objects.equals(config.getString("settings.resourcepack"), "NO_LINK") && config.getBoolean("settings.no-resourcepack-link-warning")){
            player.sendMessage(Util.papiColor(player,config.getString("messages.no-resourcepack-in-config")));
            log.warning("You didn't set any resourcepack link in the config file! Please do that in order to send a resourcepack to players!");
            return;
        }
        player.setResourcePack(Objects.requireNonNull(config.getString("settings.resourcepack")));
    }


}
