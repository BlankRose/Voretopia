/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   commands - ReloadCommand.java                        */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, July 1, 2023 11:27 PM        */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import dev.blankrose.voretopia.core.ConfigurationManager;
import dev.blankrose.voretopia.utils.PrefixBuilder;

/**
 * ReloadCommand
 * <p>
 * Command class for the /vore-reload command.
 * Reloads every configuration files of the plugin.
 * */
public class ReloadCommand implements CommandExecutor {

	// Methods
	//////////////////////////////

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length != 0)
			return false;

		// Reloads all configs
		ConfigurationManager.getInstance().loadConfigs();
		sender.sendMessage("All configs has been reloaded!");

		// Updates prefix according to the new configs
		sender.getServer().getOnlinePlayers().forEach(player -> {
			player.setPlayerListName(PrefixBuilder.getEntityPrefix(player));
		});

		return true;
	}

}