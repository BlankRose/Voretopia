package dev.blankrose.voretopia.events;

import dev.blankrose.voretopia.core.VoreActions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/// PlayerQuitsEvent
///
/// Triggered when a player leaves the server.
/// Mostly used to detach player from other players.
public class PlayerQuitsEvent implements Listener {
    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent event) {
        VoreActions.halt(event.getPlayer());
    }
}
