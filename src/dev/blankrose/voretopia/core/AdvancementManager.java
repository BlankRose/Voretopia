package dev.blankrose.voretopia.core;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

public class AdvancementManager {

    // Attributes
    //////////////////////////////

    private JavaPlugin core;
    private static final String ADVANCEMENT_DIR = "advancements/";
    private static final String CRITERIA_NAME = "trigger";

    // Constructors
    //////////////////////////////

    private static AdvancementManager instance;
    private AdvancementManager() {}

    public static AdvancementManager getInstance() {
        if (instance == null)
            instance = new AdvancementManager();
        return instance;
    }

    // Methods
    //////////////////////////////

    @Nullable
    public Advancement get(String key) {
        final NamespacedKey namespacedKey = new NamespacedKey(core, key);
        return Bukkit.getAdvancement(namespacedKey);
    }

    /*
    private boolean registerOne(String key) {
        final NamespacedKey namespacedKey = new NamespacedKey(core, key);
        try {
            byte[] in = Objects.requireNonNull(this.core.getResource(ADVANCEMENT_DIR + key + ".json")).readAllBytes();
            Bukkit.getUnsafe().loadAdvancement(namespacedKey, new String(in));
            return true;
        } catch (Exception error) {
            core.getLogger().severe("Failed to retrieve and read advancement file for \"" + key + "\"! Caused by " + error);
            return false;
        }
    }
    */

    public void registerAdvancements(JavaPlugin core) {
        if (this.core != null)
            throw new IllegalStateException("Advancements has already been initialized!");
        this.core = core;

        /*
        int failed = 0;
        final String[] advancements = {"root", "prey", "pred"};
        for (final String advancement : advancements) {
            if (!registerOne(advancement))
                ++failed;
        }
        if (failed > 0)
            this.core.getLogger().severe("Failed to register " + failed + " advancements!");
        else
            this.core.getLogger().info("Successfully registered " + advancements.length + " advancements!");
         */
    }

    public void grant(Player player, String key) {
        Advancement advancement = get(key);
        if (advancement == null)
            return;
        player.getAdvancementProgress(advancement).awardCriteria(CRITERIA_NAME);
    }

    public void revoke(Player player, String key) {
        Advancement advancement = get(key);
        if (advancement == null)
            return;
        player.getAdvancementProgress(advancement).revokeCriteria(CRITERIA_NAME);
    }

}
