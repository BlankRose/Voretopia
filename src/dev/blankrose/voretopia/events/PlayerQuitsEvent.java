package dev.blankrose.voretopia.events;

import dev.blankrose.voretopia.core.EntityWatcher;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

/// PlayerQuitsEvent
///
/// Triggered when a player leaves the server.
/// Mostly used to detach player from other players.
public class PlayerQuitsEvent implements Listener {

    // Methods
    //////////////////////////////

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        EntityWatcher watcher = new EntityWatcher(player);

        UUID pred = watcher.getPred();
        UUID[] preys = watcher.getPreys();
        Collection<? extends Player> players = player.getServer().getOnlinePlayers();

        // Removes the player who left from the predator's prey list
        players.stream()
                .filter(searched -> searched.getUniqueId().equals(pred))
                .findFirst()
                .ifPresent(result -> {
                    EntityWatcher watcher_result = new EntityWatcher(result);
                    watcher_result.removePrey(player.getUniqueId());
                });
        // Removes the player who left from prey's current predator
        // and teleport them back to their predator's location
        players.stream()
                .filter(searched -> Arrays.asList(preys).contains(searched.getUniqueId()))
                .forEach(result -> {
                    EntityWatcher watcher_result = new EntityWatcher(result);
                    watcher_result.setPred(null);
                    result.teleport(player.getLocation());
                });

        // Clears current player data
        watcher.setPred(null);
        watcher.setPreys(null);
    }
}
