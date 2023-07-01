/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   commands - GenericCompletion.java                    */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, July 1, 2023 7:25 PM         */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.commands;

import java.util.ArrayList;
import java.util.List;

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

	public List<String> getTypes() {
		return new ArrayList<String>() {{
		}};
	}

}