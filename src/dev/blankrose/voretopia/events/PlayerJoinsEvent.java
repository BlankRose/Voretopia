package dev.blankrose.voretopia.events;

import dev.blankrose.voretopia.core.EntityWatcher;
import dev.blankrose.voretopia.utils.PrefixBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/// PlayerJoinsEvent
///
/// Triggered when a player joins the server.
/// Mostly used to make the player start from clean data.
public class PlayerJoinsEvent implements Listener {

    // Methods
    //////////////////////////////

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        // Sets player's pred/prey tag
        Player player = event.getPlayer();
        String displayName = PrefixBuilder.getEntityPrefix(player);
        player.setPlayerListName(displayName);
        player.setDisplayName(displayName);

        // Clears current user data
        EntityWatcher watcher = new EntityWatcher(player);
        watcher.setPred(null);
        watcher.setPreys(null);
    }

}