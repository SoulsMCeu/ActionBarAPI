package eu.soulsmc.actionbarapi;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Joshua Samenfink
 * https://www.arrow-systems.de
 * Created on 22.10.2020 at 23:18:a
 * Coded with IntelliJ on Windows
 */

public class ActionBarMessageEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private String message;
    private boolean cancelled = false;

    public ActionBarMessageEvent(Player player, String message) {
        this.player = player;
        this.message = message;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}

