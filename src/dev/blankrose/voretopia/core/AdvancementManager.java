package dev.blankrose.voretopia.core;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class AdvancementManager {
    private AdvancementManager() {}

    @Nullable
    public static Advancement get(String key) {
        return Bukkit.getAdvancement(Provider.getKey(key));
    }

    public static void grant(Player player, String key) {
        Advancement advancement = get(key);
        if (advancement == null)
            return;
        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        progress.getRemainingCriteria()
                .forEach(progress::awardCriteria);
    }

    public static void revoke(Player player, String key) {
        Advancement advancement = get(key);
        if (advancement == null)
            return;
        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        progress.getAwardedCriteria()
                .forEach(progress::revokeCriteria);
    }
}
