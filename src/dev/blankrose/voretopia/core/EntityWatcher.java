package dev.blankrose.voretopia.core;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.*;

/// EntityWatcher
///
/// Manages custom attributes for a given entity,
/// along with all their relations to any other entities.
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

    ///
    ///
    private NamespacedKey getNamespacedKey(String key) {
        return new NamespacedKey(core, key);
    }

    /// Retrieves the currently stored data behind the given key for this entity.
    ///
    /// @param <T>              Type of the primary object
    /// @param <V>              Type of the stored object
    ///
    /// @param key              Target key for the looked up data
    /// @param type             Type of the internal persistent data
    /// @param default_value    Default value, if none is set yet.
    ///                         `null` can be passed to indicate no default value
    ///
    /// @return                 Data if any was found, otherwise `null`
    @Nullable
    private <T, V> V retrieve(String key, PersistentDataType<T, V> type, @Nullable V default_value) {
        final NamespacedKey namespaced_key = getNamespacedKey(key);
        if (default_value != null && !meta.has(namespaced_key, type))
            meta.set(namespaced_key, type, default_value);
        return meta.get(namespaced_key, type);
    }

    /// Stores the provided data behind the given key for this entity.
    ///
    /// @param <T>              Type of the primary object
    /// @param <V>              Type of the stored object
    ///
    /// @param key              Target key for the stored data
    /// @param type             Type of the internal persistent data
    /// @param value            Value to store. If `null` is provided, it will call `remove(key)` instead
    private <T, V> void store(String key, PersistentDataType<T, V> type, @Nullable V value) {
        if (value == null)
            remove(key);
        else
            meta.set(getNamespacedKey(key), type, value);
    }

    /// Deletes the stored data stored at the given key.
    ///
    /// @param key              Target key for the stored data
    private void remove(String key) {
        meta.remove(getNamespacedKey(key));
    }

    public boolean isPred() {
        return Boolean.TRUE.equals(retrieve("isPred", PersistentDataType.BOOLEAN, false));
    }

    public boolean isPrey() {
        return Boolean.TRUE.equals(retrieve("isPrey", PersistentDataType.BOOLEAN, false));
    }

    public boolean isFree() {
        return Boolean.TRUE.equals(retrieve("isFree", PersistentDataType.BOOLEAN, false));
    }

    /// Retrieves the UUID of the current predator who consumed this entity.
    ///
    /// @return             Predator's UUID, or `null` if wasn't consumed
    @Nullable
    public UUID getPred() {
        String stored = retrieve("pred", PersistentDataType.STRING, null);
        if (stored != null)
            return UUID.fromString(stored);
        return null;
    }

    /// Retrieves the list of all prey entities consumed by this entity.
    ///
    /// @return             Array of UUIDs corresponding to the consumed entities
    public UUID[] getPreys() {
        String stored = retrieve("preys", PersistentDataType.STRING, null);
        if (stored != null)
            return Arrays.stream(stored.split(":"))
                    .map(UUID::fromString)
                    .toArray(UUID[]::new);
        return new UUID[]{};
    }

    /// Defines if it's a predator, capable of devouring preys.
    /// Also grants the `Starving Path` advancement if it's a player.
    ///
    /// @param enabled      `true` is entity is a predator, otherwise `false`
    public void setPred(boolean enabled) {
        if (enabled && player != null)
            AdvancementManager.getInstance().grant(player, "pred");
        store("isPred", PersistentDataType.BOOLEAN, enabled);
    }

    /// Defines if it's a prey, capable of being devoured by predators.
    /// Also grants the `Sweet Path` advancement if it's a player.
    ///
    /// @param enabled      `true` is entity is a prey, otherwise `false`
    public void setPrey(boolean enabled) {
        if (enabled && player != null)
            AdvancementManager.getInstance().grant(player, "prey");
        store("isPrey", PersistentDataType.BOOLEAN, enabled);
    }

    /// Defines if this entity is free to grab or/and to feed.
    ///
    /// @param enabled      `true` is entity is available, otherwise `false`
    public void setFree(boolean enabled) {
        store("isFree", PersistentDataType.BOOLEAN, enabled);
    }

    /// Defines the predator that consumed this entity.
    ///
    /// @param uuid         UUID of the predator.
    ///                     Use `null` to indicate that it wasn't eaten.
    public void setPred(@Nullable UUID uuid) {
        if (uuid == null)
            remove("pred");
        else
            store("pred", PersistentDataType.STRING, uuid.toString());
    }

    /// Defines the preys that were consumed by this entity.
    ///
    /// @param uuids        List of UUIDs of the preys.
    ///                     Use `null`, or empty list to indicate no preys were eaten.
    public void setPreys(@Nullable UUID[] uuids) {
        if (uuids == null || uuids.length == 0)
            remove("preys");
        else {
            String[] values = Arrays.stream(uuids)
                    .map(UUID::toString)
                    .toArray(String[]::new);
            store("preys", PersistentDataType.STRING, String.join(":", values));
        }
    }

    /// Add a new prey to the current list of preys consumed by this entity.
    ///
    /// @param uuid         Prey's UUID to add
    public void addPrey(UUID uuid) {
        String stored = retrieve("preys", PersistentDataType.STRING, null);
        if (stored == null)
            stored = uuid.toString();
        else
            stored += ":" + uuid.toString();
        store("preys", PersistentDataType.STRING, stored);
    }

    /// Removes the given prey from the current list of preys consumed by this entity.
    ///
    /// @param uuid         Prey's UUID to remove
    public void removePrey(UUID uuid) {
        UUID[] filtered = Arrays.stream(getPreys())
                .filter(other -> !uuid.equals(other))
                .toArray(UUID[]::new);
        setPreys(filtered);
    }

    /// Registers a new attempt to swallow the prey, given by its UUID.
    /// If too many delay passes between two attempt, the internal counter restarts to 0.
    ///
    /// @param uuid                 UUID of the prey entity
    /// @param required_attempts    Defines how many attempts are required
    ///                             to successfully swallow the prey
    ///
    /// @return                     How many attempts left are needed
    ///
    /// @implNote                   In order to optimize, if `required_attempts` is 1 or lower,
    ///                             it will always return `0` instead
    public long attemptCapture(UUID uuid, long required_attempts)
    {
        if (required_attempts < 1)
            return 0;
        final long MAX_DELAY_BETWEEN_ATTEMPTS = 1500; // in millis

        String key = "capture." + uuid.toString();
        long[] values = retrieve(key, PersistentDataType.LONG_ARRAY, new long[]{0, 0});
        long cur_time = System.currentTimeMillis();
        if (cur_time - values[1] <= MAX_DELAY_BETWEEN_ATTEMPTS)
            ++values[0];
        else
            values[0] = 1;
        values[1] = cur_time;

        if (values[0] < required_attempts)
            store(key, PersistentDataType.LONG_ARRAY, values);
        else
            remove(key);
        return required_attempts - values[0];
    }
}