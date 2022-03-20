package me.gushel.resourcepackenforcer.listeners;

import me.gushel.resourcepackenforcer.ResourcePackEnforcer;
import me.gushel.resourcepackenforcer.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class PlayerResourcePackStatus implements Listener {

    @EventHandler
    public void onResourcePack(PlayerResourcePackStatusEvent event){
        Player player = event.getPlayer();
        FileConfiguration config = ResourcePackEnforcer.getInstance().getConfig();
        if (player.hasPermission(Objects.requireNonNull(config.getString("settings.bypass-permission")))) return;
        if (event.getStatus().equals(PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED)){
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.SLOW);
            if (config.getBoolean("settings.status.succesfull.enable")){
                for (String s : config.getStringList("settings.status.succesfull.commands")){
                    ResourcePackEnforcer.getInstance().getServer().dispatchCommand(ResourcePackEnforcer
                            .getInstance().getServer().getConsoleSender(), Util.papi(player,s.replace("%player%",player.getName())));
                }
            }
            me.gushel.resourcepackenforcer.objects.Player p = new me.gushel.resourcepackenforcer.objects.Player(player.getUniqueId());
            p.setStatus(PlayerResourcePackStatusEvent.Status.ACCEPTED);

            ResourcePackEnforcer.getInstance().getPlayerManager().addPlayer(p);
            return;
        }
        if (event.getStatus().equals(PlayerResourcePackStatusEvent.Status.DECLINED)){
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.SLOW);
            if (config.getBoolean("settings.status.declined.enable")){
                for (String s : config.getStringList("settings.status.declined.commands")){
                    ResourcePackEnforcer.getInstance().getServer().dispatchCommand(ResourcePackEnforcer
                            .getInstance().getServer().getConsoleSender(), Util.papi(player,s.replace("%player%",player.getName())));
                }
            }
            return;
        }
        if (event.getStatus().equals(PlayerResourcePackStatusEvent.Status.ACCEPTED)){
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.SLOW);
            if (config.getBoolean("settings.status.accepted.enable")){
                for (String s : config.getStringList("settings.status.accepted.commands")){
                    ResourcePackEnforcer.getInstance().getServer().dispatchCommand(ResourcePackEnforcer
                            .getInstance().getServer().getConsoleSender(), Util.papi(player,s.replace("%player%",player.getName())));
                }
            }

            me.gushel.resourcepackenforcer.objects.Player p = new me.gushel.resourcepackenforcer.objects.Player(player.getUniqueId());
            p.setStatus(PlayerResourcePackStatusEvent.Status.ACCEPTED);

            ResourcePackEnforcer.getInstance().getPlayerManager().addPlayer(p);
            return;
        }
        if (event.getStatus().equals(PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD)){
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.SLOW);
            if (config.getBoolean("settings.status.failed.enable")){
                for (String s : config.getStringList("settings.status.failed.commands")){
                    ResourcePackEnforcer.getInstance().getServer().dispatchCommand(ResourcePackEnforcer
                            .getInstance().getServer().getConsoleSender(), Util.papi(player,s.replace("%player%",player.getName())));
                }
            }
        }
    }

}
