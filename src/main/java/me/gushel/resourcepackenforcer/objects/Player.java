package me.gushel.resourcepackenforcer.objects;

import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import java.util.UUID;

public class Player {

    private final UUID uuid;
    private int taskID;
    private PlayerResourcePackStatusEvent.Status status;

    public Player(UUID uuid) {
        this.uuid = uuid;
    }

    public void setStatus(PlayerResourcePackStatusEvent.Status status) {
        this.status = status;
    }

    public PlayerResourcePackStatusEvent.Status getStatus() {
        return status;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getTaskID() {
        return taskID;
    }

    public UUID getUUID() {
        return uuid;
    }
}
