/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   voretopia - Voretopia.java                           */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Sunday, June 25, 2023 7:05 PM          */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import dev.blankrose.voretopia.commands.GenericCompletion;
import dev.blankrose.voretopia.core.CommandsManager;
import dev.blankrose.voretopia.core.ConfigurationManager;
import dev.blankrose.voretopia.core.CoreData;
import dev.blankrose.voretopia.core.EventsManager;

/**
 * Voretopia plugin entrypoint
 * <p>
 * By Rosie
 */
public class Voretopia extends JavaPlugin
{
	private Logger logs;

	@Override
	public void onLoad()
	{
		logs = getLogger();

		// Initialize generic classes
		CoreData.define(this);
		GenericCompletion.getInstance().init(this);
		
		// Load configs
		ConfigurationManager.getInstance().init(this)
			.addConfig("core", "configs/config.yml", "config.yml")
			.addConfig("players", "configs/players.yml", "players.yml")
			.loadConfigs();

		logs.info("Voretopia has been loaded!");
	}

	@Override
	public void onEnable()
	{
		// Register commands and events
		CommandsManager.getInstance().registerCommands(this);
		EventsManager.getInstance().registerEvents(this);

		logs.info("Voretopia is now enabled!");
	}

	@Override
	public void onDisable()
	{
		logs.info("Voretopia has been disabled!");
	}
}
