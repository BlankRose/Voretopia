/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   core - ConfigurationManager.java                     */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Sunday, June 25, 2023 12:22 PM         */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.core;

import java.io.File;
import java.io.InputStreamReader;
import java.util.TreeMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * << Singleton >>
 * ConfigManager
 * <p>
 * Singleton class which handles all of the plugin's configurations.
 * It also allows for chaining when setting up.
 * */
public class ConfigurationManager {

	// Sub-Classes
	//////////////////////////////

	private class ConfigurationEntry {
		public String resource; // Path of original base resource
		public String target; // Path of user configuration file
		public YamlConfiguration config; // Configuration object

		public ConfigurationEntry(String resource, String target, YamlConfiguration config) {
			this.resource = resource;
			this.target = target;
			this.config = config;
		}
	}

	// Attributes
	//////////////////////////////

	private Map<String, ConfigurationEntry> configs;

	private JavaPlugin core;
	private File data_folder;
	private Logger logs;

	// Constructors
	//////////////////////////////

	private static ConfigurationManager instance = null;
	private ConfigurationManager() {
		configs = new TreeMap<String, ConfigurationEntry>();
	}

	public static ConfigurationManager getInstance() {
		if (instance == null)
			instance = new ConfigurationManager();
		return instance;
	}

	// Internal Methods
	//////////////////////////////

	private boolean saveTarget(String resource, String target, YamlConfiguration config) {
		if (resource == null || target == null)
			throw new NullPointerException("Resource and target cannot be null!");

		// Creates data folder is non-existent
		if (!data_folder.exists())
			data_folder.mkdir();

		// Save default config file (or create if missing)
		File file = new File(data_folder, target);
		if (!file.exists()) {
			core.saveResource(resource, false);
			loadTarget(resource, target); // Ensures config copied over WITH comments
		} else {
			try {
				if (config == null)
					return false;
				config.save(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	private YamlConfiguration loadTarget(String resource, String target) {
		if (resource == null || target == null)
			throw new NullPointerException("Resource and target cannot be null!");

		// Loads configuration file or creates a create if missing
		File file = new File(data_folder, target);
		if (!data_folder.exists() || !file.exists())
			saveTarget(resource, target, null);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		// Appends defaults and save (in case of missing)
		config.setDefaults(YamlConfiguration.loadConfiguration(
			new InputStreamReader(core.getResource(resource)))
		);
		config.options().copyDefaults(true);

		// Re-save configuration file (in case of missing defaults)
		saveTarget(resource, target, config);
		return config;
	}

	// Methods
	//////////////////////////////

	public ConfigurationManager init(JavaPlugin self) {
		if (core != null)
			throw new IllegalStateException("ConfigurationManager was already initialized!");

		core = self;
		data_folder = self.getDataFolder();
		logs = self.getLogger();
		return this;
	}

	public ConfigurationManager addConfig(String id, String target) {
		return addConfig(id, target, target);
	}

	public ConfigurationManager addConfig(String id, String resource, String target) {
		if (id == null || resource == null || target == null)
			throw new NullPointerException("Resource and target cannot be null!");
		configs.put(id, new ConfigurationEntry(resource, target, null));
		return this;
	}

	public ConfigurationManager removeConfig(String id) {
		configs.remove(id);
		return this;
	}

	public ConfigurationManager loadConfigs() {
		if (core == null)
			throw new IllegalStateException("ConfigurationManager was not initialized!");

		int fails = 0;
		for (ConfigurationEntry entry : configs.values()) {
			entry.config = loadTarget(entry.resource, entry.target);
			if (entry.config == null)
				fails++;
		}

		logs.info("Successfully loaded all configurations!");
		if (fails > 0)
			logs.warning("Failed to load " + fails + " configuration(s)!");
		return this;
	}

	public ConfigurationManager saveConfigs() {
		if (core == null)
			throw new IllegalStateException("ConfigurationManager was not initialized!");

		int fails = 0;
		for (ConfigurationEntry entry : configs.values())
			if (!saveTarget(entry.resource, entry.target, entry.config))
				fails++;

		logs.info("Successfully saved all configurations!");
		if (fails > 0)
			logs.warning("Failed to save " + fails + " configuration(s)!");
		return this;
	}

	// Getters & Setters
	//////////////////////////////

	public Configuration getDefaultConfig(String id) {
		if (id == null)
			throw new NullPointerException("ID cannot be null!");
		return configs.get(id).config.getDefaults();
	}

	public Object getDefaultConfig(String id, String path) {
		if (id == null || path == null)
			throw new NullPointerException("Path cannot be null!");
		return configs.get(id).config.getDefaults().get(path);
	}

	public Configuration getConfig(String id) {
		if (id == null)
			throw new NullPointerException("ID cannot be null!");
		return configs.get(id).config;
	}

	public Object getConfig(String id, String path) {
		if (id == null || path == null)
			throw new NullPointerException("Path cannot be null!");
		return configs.get(id).config.get(path);
	}

	public void setConfig(String id, Configuration config) {
		if (id == null || config == null)
			throw new NullPointerException("Configuration cannot be null!");
		configs.get(id).config = (YamlConfiguration) config;
	}

	public void setConfig(String id, String path, Object value) {
		if (id == null || path == null || value == null)
			throw new NullPointerException("Path and value cannot be null!");
		configs.get(id).config.set(path, value);
	}

}
