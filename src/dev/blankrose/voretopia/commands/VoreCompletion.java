/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   commands - VoreCompletion.java                       */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Sunday, June 25, 2023 6:56 PM          */
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

	// Methods
	//////////////////////////////

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		// Subcommands selector
		if (args.length <= 1)
			return new ArrayList<String>() {{
				if (sender.hasPermission("voretopia.command.vore.set"))
					add("set");
				if (sender.hasPermission("voretopia.command.vore.setstomach"))
					add("setstomach");
				if (sender.hasPermission("voretopia.command.vore.stop"))
					add("stop");
			}};

		// Set subcommand
		if (args[0].equalsIgnoreCase("set")
			&& args.length == 2) {
				return new ArrayList<String>() {{
					add("pred");
					add("switch");
					add("prey");
				}};
		}

		// Setstomach subcommand
		if (args[0].equalsIgnoreCase("setstomach")) {

		}

		return new ArrayList<String>();
	}

}