/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   commands - ConfigCheckerCommand.java                 */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, July 1, 2023 8:50 PM         */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import dev.blankrose.voretopia.core.ConfigurationManager;

/**
 * ConfigCheckerCommand
 * <p>
 * Command class for the configchecker command.
 * It is used to check the manual validity of the configs files,
 * by printing them out to the sender.
 * */
public class ConfigCheckerCommand implements CommandExecutor {

	// Attributes
	//////////////////////////////

	private static ConfigurationManager CONFIG = ConfigurationManager.getInstance();

	// Methods
	//////////////////////////////

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Expected two arguments: <category> <key>");
			return false;
		}

		Object result = CONFIG.getConfig(args[0], args[1]);
		if (result == null)
			sender.sendMessage("Value " + args[1] + " for configuration " + args[0] + " not Found!");
		else {
			StringBuilder str = new StringBuilder();
			str.append(args[1] + " for configuration " + args[0] + " is:\n");
			str.append(" - Value: " + result.toString() + "\n");
			str.append(" - Type:  " + result.getClass().getSimpleName() + "\n");
			sender.sendMessage(str.toString());
		}
		return true;
	}

}