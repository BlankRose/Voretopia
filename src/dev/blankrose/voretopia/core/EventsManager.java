package dev.blankrose.voretopia.core;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev.blankrose.voretopia.events.PlayerQuitsEvent;
import dev.blankrose.voretopia.events.PlayerInteractEvent;
import dev.blankrose.voretopia.events.PlayerJoinsEvent;

/// << Singleton >>
/// EventsManager
///
/// Registers and handles events of the plugin.
public class EventsManager {
    private EventsManager() {}

    /// Registers all events of the plugin.
    public static void registerEvents() {
        JavaPlugin core = Provider.getPlugin();
        PluginManager manager = core.getServer().getPluginManager();

        manager.registerEvents(new PlayerInteractEvent(), core);
        manager.registerEvents(new PlayerJoinsEvent(), core);
        manager.registerEvents(new PlayerQuitsEvent(), core);

        core.getLogger().info("All events has been successfully registered!");
    }
}