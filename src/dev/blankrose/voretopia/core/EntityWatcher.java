/* ************************************************************************** */
/*          .-.                                                               */
/*    __   /   \   __                                                         */
/*   (  `'.\   /.'`  )   core - EntityWatcher.java                            */
/*    '-._.(;;;)._.-'                                                         */
/*    .-'  ,`"`,  '-.                                                         */
/*   (__.-'/   \'-.__)   By: Rosie (https://github.com/BlankRose)             */
/*       //\   /         Last Updated: Saturday, July 1, 2023 5:54 PM         */
/*      ||  '-'                                                               */
/* ************************************************************************** */

package dev.blankrose.voretopia.core;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * << Singleton >>
 * PlayersManager
 * <p>
 * Manages all players on the server, along with all their relations
 * to other players and entities.
 * */
public class EntityWatcher {

	// Attributes
	//////////////////////////////

	private static JavaPlugin core;
	private final PersistentDataContainer meta;
	private final Player player;

	// Constructors
	//////////////////////////////

	public EntityWatcher(Entity entity) {
		if (core == null)
			throw new IllegalStateException("Watcher has not been initialized!");
		this.meta = entity.getPersistentDataContainer();
		if (entity instanceof Player)
			this.player = (Player) entity;
		else
			this.player = null;
	}

	// Methods
	//////////////////////////////

	public static void init(JavaPlugin core) {
		if (EntityWatcher.core != null)
			throw new IllegalStateException("Watcher has already been initialized!");
		EntityWatcher.core = core;
	}

	private NamespacedKey getNamespacedKey(String key) {
		return new NamespacedKey(core, key);
	}

	public <T, V> V retrieve(String key, PersistentDataType<T, V> type, V default_value) {
		if (!meta.has(getNamespacedKey(key), type))
			meta.set(getNamespacedKey(key), type, default_value);
		return meta.get(getNamespacedKey(key), type);
	}

	public <T, V> void store(String key, PersistentDataType<T, V> type, V value) {
		meta.set(getNamespacedKey(key), type, value);
	}

	public boolean isPred() {
		return retrieve("isPred", PersistentDataType.BOOLEAN, false);
	}

	public boolean isPrey() {
		return retrieve("isPrey", PersistentDataType.BOOLEAN, false);
	}

	public boolean isFree() {
		return retrieve("isFree", PersistentDataType.BOOLEAN, false);
	}

	public long getPred() {
		return retrieve("pred", PersistentDataType.LONG, 0L);
	}

	public long[] getPreys() {
		return retrieve("preys", PersistentDataType.LONG_ARRAY, new long[0]);			
	}

	/// Defines if it's a predator, capable of devouring preys
	public void setPred(boolean enabled) {
		if (enabled && player != null)
			AdvancementManager.getInstance().grant(player, "pred");
		store("isPred", PersistentDataType.BOOLEAN, enabled);
	}

	/// Defines if it's a prey, capable of being devoured by predators
	public void setPrey(boolean enabled) {
		if (enabled && player != null)
			AdvancementManager.getInstance().grant(player, "prey");
		store("isPrey", PersistentDataType.BOOLEAN, enabled);
	}

	public void setFree(boolean enabled) {
		store("isFree", PersistentDataType.BOOLEAN, enabled);
	}

	public void setPred(long uuid) {
		store("pred", PersistentDataType.LONG, uuid);
	}

	public void setPreys(long[] uuids) {
		store("preys", PersistentDataType.LONG_ARRAY, uuids);
	}

}