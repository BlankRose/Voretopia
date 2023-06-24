/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   core - EventsManager.java                            */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Sunday, June 25, 2023 12:02 AM         */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.core;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev.blankrose.voretopia.events.PlayerEvent;

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
	private PluginManager manager;

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
		this.manager = core.getServer().getPluginManager();

		// Register events
		manager.registerEvents(new PlayerEvent(), core);
		core.getLogger().info("All events has been successfully registered!");
	}

}