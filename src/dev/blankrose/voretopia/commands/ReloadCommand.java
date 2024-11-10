package dev.blankrose.voretopia.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import dev.blankrose.voretopia.core.ConfigurationManager;
import dev.blankrose.voretopia.utils.PrefixBuilder;

import javax.annotation.Nonnull;

/// ReloadCommand
///
/// Command class for the /vore-reload command.
/// Reloads every configuration files of the plugin.
public class ReloadCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
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