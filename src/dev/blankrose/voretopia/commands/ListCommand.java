/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   commands - ListCommand.java                          */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, July 1, 2023 9:33 PM         */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.blankrose.voretopia.core.EntityWatcher;

/**
 * ListCommand
 * <p>
 * Command which lists all of the players in the server, along with their
 * respective roles and current relationships.
 * */
public class ListCommand implements CommandExecutor {

	// Methods
	//////////////////////////////

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Prepare the lists
		List<Player> bystanders = new ArrayList<Player>();
		List<Player> preds = new ArrayList<Player>();
		List<Player> preys = new ArrayList<Player>();
		List<Player> switchs = new ArrayList<Player>();
		List<Player> free = new ArrayList<Player>();

		// Run through all players and sort them into their respective lists
		sender.getServer().getOnlinePlayers().forEach(player -> {
			EntityWatcher watcher = new EntityWatcher(player);
			if (watcher.isPred() && watcher.isPrey())
				switchs.add(player);
			else if (watcher.isPred())
				preds.add(player);
			else if (watcher.isPrey())
				preys.add(player);
			else
				bystanders.add(player);
			if (watcher.isFree())
				free.add(player);
		});

		// Format and send the message
		StringBuilder str = new StringBuilder();
		str.append("  Player Vore List  \n");
		str.append("--------------------\n\n");
		str.append("Bystanders: " + bystanders.size() + "\n");
		bystanders.forEach(player -> str.append(" - " + player.getName() + "\n"));
		str.append("Predators: " + preds.size() + "\n");
		preds.forEach(player -> str.append(" - " + (free.contains(player) ? "[Free] " : "") + player.getName() + "\n"));
		str.append("Preys: " + preys.size() + "\n");
		preys.forEach(player -> str.append(" - " + (free.contains(player) ? "[Free] " : "") + player.getName() + "\n"));
		str.append("Switches: " + switchs.size() + "\n");
		switchs.forEach(player -> str.append(" - " + (free.contains(player) ? "[Free] " : "") + player.getName() + "\n"));
		str.append("Total Players: " + sender.getServer().getOnlinePlayers().size() + "\n");
		str.append("--------------------");
		sender.sendMessage(str.toString());

		// Clear the lists
		bystanders.clear();
		preds.clear();
		preys.clear();
		switchs.clear();
		free.clear();

		return true;
	}

}