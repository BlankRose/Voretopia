package dev.blankrose.voretopia.events;

import dev.blankrose.voretopia.core.EntityWatcher;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

/**
 * PlayerInteractEvent
 * <p>
 * Event class for the PlayerInteractEvent event.
 * Triggered when a player interacts with an entity.
 * */
public class PlayerInteractEvent implements Listener {

    private final static long ATTEMPTS_REQUIRED = 10;
    private final static int PROGRESS_BAR_LENGTH = 10;

    // Methods
    //////////////////////////////

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEntityEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND)
            return;

        Player caller = event.getPlayer();
        if (caller.isSneaking()
                && caller.getEquipment().getItemInMainHand().getType() == Material.AIR
                && caller.getEquipment().getItemInOffHand().getType() == Material.AIR
                && event.getRightClicked() instanceof Player target
                && caller.getLocation().distanceSquared(target.getLocation()) <= 6) {
            EntityWatcher watcher_caller = new EntityWatcher(caller);
            EntityWatcher watcher_target = new EntityWatcher(target);

            if (!watcher_caller.isPred() || !watcher_target.isPrey()
                    || watcher_caller.getPred() != watcher_target.getPred())
                return;
            target.addPotionEffects(Arrays.asList(
                    new PotionEffect(PotionEffectType.SLOWNESS, 40, 4, true),
                    new PotionEffect(PotionEffectType.DARKNESS, 40, 1, true)
            ));

            long gulpLeft = watcher_target.attemptCapture(caller.getUniqueId(), ATTEMPTS_REQUIRED);
            if (gulpLeft <= 0) {
                caller.spigot().sendMessage(
                        ChatMessageType.ACTION_BAR,
                        new TextComponent("" + ChatColor.GREEN + ChatColor.BOLD + "* GULP~ *")
                );
                watcher_target.setPred(caller.getUniqueId());
                watcher_caller.addPrey(target.getUniqueId());
                target.teleport(caller.getLocation());
            }
            else {
                final float progress = (float) gulpLeft / (float) ATTEMPTS_REQUIRED;
                int counter = (int) (progress * (float) PROGRESS_BAR_LENGTH);
                caller.spigot().sendMessage(
                        ChatMessageType.ACTION_BAR,
                        new TextComponent(
                                ChatColor.DARK_GREEN + "■".repeat(PROGRESS_BAR_LENGTH - counter) +
                                ChatColor.GREEN + "■".repeat(counter)
                        )
                );
            }
        }
    }

}