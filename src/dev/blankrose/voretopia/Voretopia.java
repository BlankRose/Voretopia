/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   voretopia - Voretopia.java                           */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, July 1, 2023 7:50 PM         */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia;

import java.util.logging.Logger;

import dev.blankrose.voretopia.core.*;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Voretopia plugin entrypoint
 * <p>
 * By Rosie
 * */
public class Voretopia extends JavaPlugin
{
	private Logger logs;

	@Override
	public void onLoad()
	{
		logs = getLogger();

		// Initialize everything
		EntityWatcher.init(this);
		ConfigurationManager.getInstance().init(this)
			.addConfig("core", "configs/config.yml", "config.yml")
			.addConfig("players", "configs/players.yml", "players.yml")
			.loadConfigs();

		// Print success message
		logs.info("Voretopia has been loaded!");
	}

	@Override
	public void onEnable()
	{
		// Register commands and events
		CommandsManager.getInstance().registerCommands(this);
		EventsManager.getInstance().registerEvents(this);
		AdvancementManager.getInstance().registerAdvancements(this);

		// Print success message
		logs.info("Voretopia is now enabled!");
	}

	@Override
	public void onDisable()
	{
		// Print success message
		logs.info("Voretopia has been disabled!");
	}
}
