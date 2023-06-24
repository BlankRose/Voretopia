/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   core - CoreData.java                                 */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, June 24, 2023 10:40 PM       */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.core;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

/**
 * << Non-Instantiable >>
 * CoreValues
 * <p>
 * Class storing all of the plugin's major constants,
 * used throughout the plugin's codebase
 * */
public final class CoreData {

	// Attributes
	//////////////////////////////

	private static Plugin core = null;

	private static String name;
	private static String version;

	private static Logger logger;
	private static File data_folder;

	private static String base_config_id;
	private static String base_config_file;

	// Constructors
	//////////////////////////////

	private CoreData() {
		throw new UnsupportedOperationException();
	}

	public static void define(Plugin self) {
		if (core != null)
			throw new UnsupportedOperationException("CoreData has been already defined!");
		core = self;

		name = self.getName();
		data_folder = self.getDataFolder();
		logger = self.getLogger();

		version = "1.0.0";
		base_config_id = "core";
		base_config_file = "config.yml";
	}

	// Getters & Setters
	//////////////////////////////

	// Any access to base plugin should be done through this package
	static Plugin getCore() { return core; }

	public static String getName() { return name; }
	public static Logger getLogger() { return logger; }
	public static String getVersion() { return version; }
	public static File getDataFolder() { return data_folder; }
	public static String getBaseConfigID() { return base_config_id; }
	public static String getBaseConfigFile() { return base_config_file; }

}