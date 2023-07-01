/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   utils - PrefixBuilder.java                           */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, July 1, 2023 11:30 PM        */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import dev.blankrose.voretopia.core.ConfigurationManager;
import dev.blankrose.voretopia.core.EntityWatcher;

/**
 * << Static >>
 * PrefixBuilder
 * <p>
 * Generates various prefixes for the players using the plugin.
 */
public class PrefixBuilder {

	// Attributes
	//////////////////////////////

	private static ConfigurationManager CONFIG = ConfigurationManager.getInstance();

	// Constructors
	//////////////////////////////

	private PrefixBuilder() {}

	// Methods
	//////////////////////////////

	private static String retrievePrefix(String key) {
		return CONFIG.getConfig("core", key, String.class);
	}

	public static String getPrefix(boolean isPred, boolean isPrey, boolean isFree) {
		if (isPred && isPrey) {
			if (isFree)
				return retrievePrefix("prefix-freeswitch");
			else
				return retrievePrefix("prefix-switch");
		}
		else if (isPred) {
			if (isFree)
				return retrievePrefix("prefix-freepred");
			else
				return retrievePrefix("prefix-pred");
		}
		else if (isPrey) {
			if (isFree)
				return retrievePrefix("prefix-freeprey");
			else
				return retrievePrefix("prefix-prey");
		}
		else
			return retrievePrefix("prefix-bystander");
	}

	public static String getEntityPrefix(Entity entity) {
		EntityWatcher watcher = new EntityWatcher(entity);

		if (CONFIG.getConfig("core", "roles-prefix", Boolean.class)) {
			return getPrefix(watcher.isPred(), watcher.isPrey(), watcher.isFree())
				+ " " + (entity instanceof Player
					? ((Player) entity).getDisplayName()
					: entity.getCustomName()
				);
		}

		else {
			return ((entity instanceof Player
				? ((Player) entity).getDisplayName()
				: entity.getCustomName()
			));
		}
	}

}