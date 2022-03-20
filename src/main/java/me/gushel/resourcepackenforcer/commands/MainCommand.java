package me.gushel.resourcepackenforcer.commands;

import me.gushel.resourcepackenforcer.ResourcePackEnforcer;
import me.gushel.resourcepackenforcer.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        FileConfiguration config = ResourcePackEnforcer.getInstance().getConfig();
        if (sender instanceof ConsoleCommandSender){
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    Reload.onCommand();
                    sender.sendMessage(Util.color(config.getString("messages.reload")));
                    return true;
                }
                return true;
            }
            if (args.length == 2){
                if (args[0].equalsIgnoreCase("send")){
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target == null){
                        sender.sendMessage(Util.color(config.getString("messages.invalid-target")));
                        return true;
                    }
                    Send.onCommand(target);
                    sender.sendMessage(Util.color(Objects.requireNonNull(config.getString("messages.send-texturepack")).replace("%player%",target.getName())));
                }
                return true;
            }
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!player.hasPermission("resourcepackenforcer.reload")){
                    player.sendMessage(Util.papiColor(player,config.getString("messages.no-perm")));
                    return true;
                }
                Reload.onCommand();
                player.sendMessage(Util.papiColor(player,config.getString("messages.reload")));
                return true;
            }
            return true;
        }
        if (args.length == 2){
            if (args[0].equalsIgnoreCase("send")){
                if (!player.hasPermission("resourcepackenforcer.send")){
                    player.sendMessage(Util.papiColor(player,config.getString("messages.no-perm")));
                    return true;
                }
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null){
                    player.sendMessage(Util.papiColor(player,config.getString("messages.invalid-target")));
                    return true;
                }
                Send.onCommand(target);
                player.sendMessage(Util.papiColor(player, Objects.requireNonNull(config.getString("messages.send-texturepack")).replace("%player%",target.getName())));
            }
            return true;
        }
        return true;
    }

}
