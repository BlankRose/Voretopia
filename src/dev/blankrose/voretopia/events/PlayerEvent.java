/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   events - PlayerEvent.java                            */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Sunday, June 25, 2023 12:09 AM         */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.events;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * PlayerInteractEvent
 * <p>
 * Event class for the PlayerInteractEvent event.
 * Triggered when a player interacts with an entity.
 * */
public class PlayerEvent implements Listener {

	// Methods
	//////////////////////////////

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEntityEvent event) {
		if (event.getPlayer().isSneaking()
			&& event.getRightClicked().getType() == EntityType.PLAYER) {

			// TODO: Implement event
			event.getRightClicked().teleport(new Location(event.getPlayer().getWorld(), 0, 100, 0));
		}
	}

}