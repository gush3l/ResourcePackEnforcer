package me.gushel.resourcepackenforcer.managers;

import me.gushel.resourcepackenforcer.ResourcePackEnforcer;
import me.gushel.resourcepackenforcer.objects.Player;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class PlayerManager {

    private final List<Player> cache;
    private int taskID;

    public PlayerManager() {
        cache = new LinkedList<>();
    }

    public void addPlayer(Player player) {
        cache.add(player);
    }

    public void removePlayer(UUID uuid) {
        Player player = cache.stream().filter(p -> p.getUUID() == uuid).findFirst().orElse(null);
        cache.remove(player);
    }

    public Player getPlayer(UUID uuid) {
        return cache.stream().filter(p -> p.getUUID() == uuid).findFirst().orElse(null);
    }

    public List<Player> getCache() {
        return cache;
    }

    public void task() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(ResourcePackEnforcer.getInstance(), () -> {
            for(org.bukkit.entity.Player player :  Bukkit.getOnlinePlayers()) {
                if(getPlayer(player.getUniqueId()) == null) return;
                if(getPlayer(player.getUniqueId()).getTaskID() == 0) return;

                PotionEffect blind = player.getPotionEffect(PotionEffectType.BLINDNESS);
                PotionEffect slow = player.getPotionEffect(PotionEffectType.SLOW);

                if(getPlayer(player.getUniqueId()).getStatus() == PlayerResourcePackStatusEvent.Status.ACCEPTED
                        || getPlayer(player.getUniqueId()).getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED
                        && player.getActivePotionEffects().contains(blind) && player.getActivePotionEffects().contains(slow)) {
                    player.removePotionEffect(PotionEffectType.BLINDNESS);
                    player.removePotionEffect(PotionEffectType.SLOW);
                }
            }
        }, 0, 20);
    }

    public int getTaskID() {
        return taskID;
    }

    public void clear() {
        cache.clear();
    }
}
