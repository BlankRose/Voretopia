/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   commands - VoreCommand.java                          */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, July 1, 2023 11:35 PM        */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.blankrose.voretopia.core.EntityWatcher;
import dev.blankrose.voretopia.utils.PrefixBuilder;

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
			sender.sendMessage(ChatColor.RED + label + " can only be executed by a player!");
			return true;
		}

		if (args.length == 0)
			return false;
		Player player = (Player) sender;

		switch (args[0].toLowerCase()) {
			case "help":
				helpSubCommand(player);
				break;

			case "set":
				if (!player.hasPermission("voretopia.command.vore.set"))
					player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				else
					setSubCommand(player, args);
				break;

			case "setstomach":
				if (!player.hasPermission("voretopia.command.vore.setstomach"))
					player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				else
					setStomachSubCommand(player, args);
				break;

			case "stop":
				if (!player.hasPermission("voretopia.command.vore.stop"))
					player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				else
					stopSubCommand(player);
				break;

			default:
				player.sendMessage(ChatColor.RED + "Unknown subcommand!");
				return false;
		}
		return true;
	}

	// Subcommands
	//////////////////////////////

	private void helpSubCommand(Player player) {
		StringBuilder str = new StringBuilder();
		str.append(ChatColor.GOLD + "VoreTopia Help\n");
		str.append(ChatColor.GOLD + "--------------\n");
		str.append(ChatColor.GOLD + "/vore help - " + ChatColor.WHITE + "Displays this help message.\n");
		str.append(ChatColor.GOLD + "/vore set <role> [type] - " + ChatColor.WHITE + "Sets your role, along with the vore type.\n");
		str.append(ChatColor.GOLD + "/vore setstomach <type> - " + ChatColor.WHITE + "Sets your stomachs position.\n");
		str.append(ChatColor.GOLD + "/vore stop - " + ChatColor.WHITE + "Stops the current vore interactions.\n");
		player.sendMessage(str.toString());
	}

	private void setSubCommand(Player player, String[] args) {
		if (args.length == 1) {
			player.sendMessage(ChatColor.RED + "You must specify a vore type!");
			return;
		}

		String selection = args[1].toLowerCase();
		boolean free = selection.startsWith("f") || selection.startsWith("free");
		EntityWatcher watcher = new EntityWatcher(player);

		if (selection.endsWith("bystander") || selection.endsWith("none")
			|| selection.endsWith("neutral") || selection.endsWith("off")) {
			stopSubCommand(player);
			return;
		}

		else if (selection.endsWith("pred") || selection.endsWith("predator")) {
			watcher.setPred(true);
			watcher.setPrey(false);
			player.sendMessage(ChatColor.GREEN + "You are now a predator!");
		}

		else if (selection.endsWith("switch") || selection.endsWith("predprey")
			|| selection.endsWith("both") || selection.endsWith("all")) {
			watcher.setPred(true);
			watcher.setPrey(true);
			player.sendMessage(ChatColor.GREEN + "You are now a predator and prey!");
		}

		else if (selection.endsWith("prey") || selection.endsWith("food")) {
			watcher.setPred(false);
			watcher.setPrey(true);
			player.sendMessage(ChatColor.GREEN + "You are now prey!");
		}

		else {
			player.sendMessage(ChatColor.RED + "Invalid vore type!");
			return;
		}

		watcher.setFree(free);
		player.setPlayerListName(PrefixBuilder.getEntityPrefix(player));
	}

	private void setStomachSubCommand(Player player, String[] args) {
		// TODO: Implement setstomach
		player.sendMessage(ChatColor.RED + "setStomachSubCommand is not yet implemented!");
	}

	private void stopSubCommand(Player player) {
		// TODO: Implement releasing process (release prey and get out of preds)
		EntityWatcher watcher = new EntityWatcher(player);
		watcher.setPred(false);
		watcher.setPrey(false);
		watcher.setFree(false);

		player.sendMessage(ChatColor.GREEN + "You are no longer a predator or prey!");
		player.setPlayerListName(PrefixBuilder.getEntityPrefix(player));
	}

}