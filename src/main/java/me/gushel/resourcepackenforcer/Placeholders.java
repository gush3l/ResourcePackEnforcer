package me.gushel.resourcepackenforcer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "resourcepackenforcer";
    }

    @Override
    public @NotNull String getAuthor() {
        return "gush3l";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String identifier) {
        FileConfiguration config = ResourcePackEnforcer.getInstance().getConfig();

        if (player == null) {
            return "";
        }

        Player player1 = player.getPlayer();

        if (identifier.equals("boolean")){
            try {
                assert player1 != null;
                if (ResourcePackEnforcer.getInstance().getPlayerManager().getPlayer(player1.getUniqueId()) != null)
                    return "yes";
                return "no";
            } catch(NullPointerException e) {
                return null;
            }
        }

        if (identifier.equals("using")){
            return String.valueOf(ResourcePackEnforcer.getInstance().getPlayerManager().getCache().size());
        }

        if (identifier.equals("notusing")){
            return String.valueOf(Bukkit.getServer().getOnlinePlayers().size()-ResourcePackEnforcer.getInstance().getPlayerManager().getCache().size());
        }

        if (identifier.equals("link")){
            return config.getString("settings.resourcepack");
        }

        return "";
    }

}
