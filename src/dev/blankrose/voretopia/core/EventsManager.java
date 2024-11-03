/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   core - EventsManager.java                            */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, July 1, 2023 9:47 PM         */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.core;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev.blankrose.voretopia.events.PlayerQuitsEvent;
import dev.blankrose.voretopia.events.PlayerInteractEvent;
import dev.blankrose.voretopia.events.PlayerJoinsEvent;

/**
 * << Singleton >>
 * EventsManager
 * <p>
 * Registers and handles events of the plugin.
 * */
public class EventsManager {

	// Attributes
	//////////////////////////////

	private JavaPlugin core;

    // Constructors
	//////////////////////////////

	private static EventsManager instance;
	private EventsManager() {}

	public static EventsManager getInstance() {
		if (instance == null)
			instance = new EventsManager();
		return instance;
	}

	// Methods
	//////////////////////////////

	/**
	 * Registers all events of the plugin.
	 * */
	public void registerEvents(JavaPlugin core) {
		if (this.core != null)
			throw new IllegalStateException("Events has already been initialized!");
		this.core = core;
        PluginManager manager = core.getServer().getPluginManager();

		// Register events
		manager.registerEvents(new PlayerInteractEvent(), core);
		manager.registerEvents(new PlayerJoinsEvent(), core);
		manager.registerEvents(new PlayerQuitsEvent(), core);

		core.getLogger().info("All events has been successfully registered!");
	}

}