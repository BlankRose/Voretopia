/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   events - PlayerJoinsEvent.java                       */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, July 1, 2023 11:42 PM        */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.blankrose.voretopia.utils.PrefixBuilder;

/**
 * PlayerJoinsEvent
 * <p>
 * Event class for the PlayerJoinsEvent event.
 * Triggered when a player joins the server.
 * */
public class PlayerJoinsEvent implements Listener {

	// Methods
	//////////////////////////////

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		// Retrieve player information
		Player player = event.getPlayer();

		// Update player's display name
		player.setPlayerListName(PrefixBuilder.getEntityPrefix(player));
	}

}