/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   voretopia - Voretopia.java                           */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Sunday, June 25, 2023 12:05 AM         */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

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
		CoreData.define(this);

		logs = getLogger();
		ConfigurationManager.getInstance()
			.addConfig(CoreData.getBaseConfigID(), CoreData.getBaseConfigFile())
			.loadConfigs();

		logs.info("Voretopia has been loaded!");
	}

	@Override
	public void onEnable()
	{
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
