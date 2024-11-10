/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   commands - VoreCompletion.java                       */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, July 1, 2023 7:10 PM         */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 * VoreCompletion
 * <p>
 * Tab completion class for the /vore command.
 * */
public class VoreCompletion implements TabCompleter {

	// Attributes
	//////////////////////////////

	private static GenericCompletion GENERIC = GenericCompletion.getInstance();

	// Methods
	//////////////////////////////

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		// Subcommands selector
		if (args.length <= 1)
			return new ArrayList<String>() {{
				add("help");
				if (sender.hasPermission("voretopia.command.vore.set"))
					add("set");
				if (sender.hasPermission("voretopia.command.vore.setstomach"))
					add("setstomach");
				if (sender.hasPermission("voretopia.command.vore.stop"))
					add("stop");
				if (sender.hasPermission("voretopia.command.vore.release"))
					add("release");
				if (sender.hasPermission("voretopia.command.vore.escape"))
					add("escape");
			}};

		// Set subcommand
		if (args[0].equalsIgnoreCase("set") && args.length == 2
			&& sender.hasPermission("voretopia.command.vore.set")) {
			return GENERIC.getRoles();
		}

		// Setstomach subcommand
		if (args[0].equalsIgnoreCase("setstomach")
			&& sender.hasPermission("voretopia.command.vore.setstomach")) {
			// TODO: Add stomach types
		}

		// Returns empty list when none exists for given context
		return new ArrayList<>();
	}

}