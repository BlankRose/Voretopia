/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   core - CommandsManager.java                          */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Sunday, June 25, 2023 5:22 PM          */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import dev.blankrose.voretopia.commands.CancelCompletion;
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

	private void registerSingle(String name, CommandExecutor exec, TabCompleter completer) {
		PluginCommand cmd = core.getCommand(name);
		cmd.setExecutor(exec);
		cmd.setTabCompleter(completer);
	}

	/**
	 * Registers all commands of the plugin.
	 * */
	public void registerCommands(JavaPlugin core) {
		if (this.core != null)
			throw new IllegalStateException("Commands has already been initialized!");
		this.core = core;

		// Register commands
		core.getCommand("vore").setExecutor(new VoreCommand());

		registerSingle("vore-reload",
			new ReloadCommand(),
			new CancelCompletion());

		core.getLogger().info("All commands has been successfully registered!");
	}

	public Command getCommand(String name) {
		return core.getCommand(name);
	}

}
