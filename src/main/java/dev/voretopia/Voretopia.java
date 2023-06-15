package dev.voretopia;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * voretopia java plugin
 */
public class Voretopia extends JavaPlugin
{
	private static final Logger LOGGER = Logger.getLogger("voretopia");

	public void onEnable()
	{
		LOGGER.info("Voretopia enabled");
	}

	public void onDisable()
	{
		LOGGER.info("Voretopia disabled");
	}
}
