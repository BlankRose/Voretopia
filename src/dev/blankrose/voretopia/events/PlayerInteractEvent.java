/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   events - PlayerInteractEvent.java                    */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, July 1, 2023 7:04 PM         */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * PlayerInteractEvent
 * <p>
 * Event class for the PlayerInteractEvent event.
 * Triggered when a player interacts with an entity.
 * */
public class PlayerInteractEvent implements Listener {

	// Methods
	//////////////////////////////

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEntityEvent event) {
		Player caller = event.getPlayer();
		Entity target = event.getRightClicked();
		if (caller.isSneaking()
			&& target.getType() == EntityType.PLAYER) {

			// TODO: Implement event
			target.teleport(caller.getLocation());
		}
	}

}