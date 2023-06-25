/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   core - PlayersManager.java                           */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Sunday, June 25, 2023 11:59 AM         */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.core;

/**
 * << Singleton >>
 * PlayersManager
 * <p>
 * Manages all players on the server, along with all their relations
 * to other players and entities.
 * */
public class PlayersManager {

	// Attributes
	//////////////////////////////

	// Constructors
	//////////////////////////////

	private static PlayersManager instance = null;
	private PlayersManager() {}

	public static PlayersManager getInstance() {
		if (instance == null) {
			instance = new PlayersManager();
		}
		return instance;
	}

	// Methods
	//////////////////////////////

}