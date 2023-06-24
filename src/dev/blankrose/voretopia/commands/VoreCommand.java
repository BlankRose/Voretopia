/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   commands - VoreCommand.java                          */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, June 24, 2023 10:45 PM       */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * VoreCommand
 * <p>
 * Command class for the /vore command.
 * Provides access to all basic vore-related commands.
 * */
public class VoreCommand implements CommandExecutor {

	// Methods
	//////////////////////////////

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§e" + label + " can only be executed by a player!");
			return true;
		}

		if (args.length == 0) { return false; }
		Player player = (Player) sender;

		// TODO: Implement command

		return true;
	}

}