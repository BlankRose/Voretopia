package dev.blankrose.voretopia;

import dev.blankrose.voretopia.core.*;
import org.bukkit.plugin.java.JavaPlugin;

/// Plugin's entrypoint
public class Voretopia extends JavaPlugin {
	@Override
	public void onLoad()
	{
		Provider.init(this);
		ConfigurationManager.getInstance()
			.addConfig("core", "configs/config.yml", "config.yml")
			.addConfig("players", "configs/players.yml", "players.yml")
			.loadConfigs();
		getLogger().info("Voretopia has been loaded!");
	}

	@Override
	public void onEnable()
	{
		CommandsManager.registerCommands();
		EventsManager.registerEvents();
		getLogger().info("Voretopia is now enabled!");
	}

	@Override
	public void onDisable()
	{
		getLogger().info("Voretopia has been disabled!");
	}
}
