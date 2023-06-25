/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   commands - GenericCompletion.java                    */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Sunday, June 25, 2023 7:49 PM          */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import dev.blankrose.voretopia.core.ConfigurationManager;

/**
 * << Singleton >>
 * GenericCompletion
 * <p>
 * Provides generic tab completion definitions for use in multiple commands.
 * */
public class GenericCompletion {

	// Attributes
	//////////////////////////////

	private ConfigurationManager config;
	private JavaPlugin core;

	// Constructors
	//////////////////////////////

	private static GenericCompletion instance = null;
	private GenericCompletion() {
		config = ConfigurationManager.getInstance();
	}

	public static GenericCompletion getInstance() {
		if (instance == null)
			instance = new GenericCompletion();
		return instance;
	}

	// Methods
	//////////////////////////////

	public void init(JavaPlugin core) {
		if (this.core != null)
			throw new IllegalStateException("Generic Completion has already been initialized!");
		this.core = core;
	}

	public List<String> getEmpty() {
		return new ArrayList<String>();
	}

	public List<String> getRoles() {
		return new ArrayList<String>() {{
			add("bystander");
			add("pred");
			add("switch");
			add("prey");

			if (config.getConfig("core").getBoolean("free-roles")) {
				add("fpred");
				add("fswitch");
				add("fprey");
			}
		}};
	}

}