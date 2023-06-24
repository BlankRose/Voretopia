/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   core - CommandsManager.java                          */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, June 24, 2023 10:48 PM       */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.core;

import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import dev.blankrose.voretopia.commands.ReloadCommand;
import dev.blankrose.voretopia.commands.VoreCommand;

/**
 * << Singleton >>
 * CommandsManager
 * <p>
 * Registers and handles commands of the plugin.
 * */
public class CommandsManager {

	// Attributes
	//////////////////////////////

	private JavaPlugin core;

	// Constructors
	//////////////////////////////

	private static CommandsManager instance;
	private CommandsManager() {}

	public static CommandsManager getInstance() {
		if (instance == null)
			instance = new CommandsManager();
		return instance;
	}

	// Methods
	//////////////////////////////

	/**
	 * Registers all commands of the plugin.
	 * */
	public void registerCommands(JavaPlugin core) {
		if (this.core != null)
			throw new IllegalStateException("Commands has already been initialized!");
		this.core = core;

		// Register commands
		core.getCommand("vore").setExecutor(new VoreCommand());
		core.getCommand("vore-reload").setExecutor(new ReloadCommand());
		core.getLogger().info("All commands has been successfully registered!");
	}

	public Command getCommand(String name) {
		return core.getCommand(name);
	}

}
