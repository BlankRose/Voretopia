package dev.blankrose.voretopia.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/// Provides useful generic function, related to any kind of voracious actions
public class VoreActions {

    /// Retrieves the apex predator, at the very top of a voreception chain.
    ///
    /// @param prey             Prey looking for its top predator
    /// @return                 Apex predator, at the very top of the chain,
    ///                         or `null` is the prey has no predator.
    @Nullable
    private static Entity getApexPredator(@Nullable Entity prey) {
        Entity apex_pred = null;
        while (prey != null) {
            EntityWatcher watcher = new EntityWatcher(prey);
            UUID pred = watcher.getPred();
            if (pred == null)
                break;
            apex_pred = Bukkit.getEntity(pred);
            prey = apex_pred;
        }
        return apex_pred;
    }

    /// Makes the target predator swallows down the target prey.
    ///
    /// @param pred         Predator ready to swallow the prey
    /// @param prey         Prey about to get swallowed by the predator
    public static void swallow(@Nonnull Entity pred, @Nonnull Entity prey) {
        // TODO: Implement swallow() function
    }

    /// Releases consumed preys. If the pred is itself inside another pred, then
    /// the released preys becomes the higher predator's preys.
    ///
    /// @param pred             Predator who release the preys
    public static void release(@Nonnull Entity pred) {
        EntityWatcher watcher = new EntityWatcher(pred);
        UUID apex_pred = watcher.getPred();
        UUID[] preys = watcher.getPreys();
        Collection<? extends Player> players = Provider.getOnlinePlayers();

        players.stream()
                .filter(searched -> searched.getUniqueId().equals(apex_pred))
                .findFirst()
                .ifPresentOrElse(
                        result -> {
                            EntityWatcher watcher_pred = new EntityWatcher(result);
                            players.stream()
                                    .filter(searched -> Arrays.asList(preys).contains(searched.getUniqueId()))
                                    .forEach(prey -> {
                                        EntityWatcher watcher_prey = new EntityWatcher(prey);
                                        watcher_pred.addPrey(prey.getUniqueId());
                                        watcher_prey.setPred(result.getUniqueId());
                                        prey.teleport(pred);
                                    });
                        },
                        () -> players.stream()
                                .filter(searched -> Arrays.asList(preys).contains(searched.getUniqueId()))
                                .forEach(prey -> {
                                    EntityWatcher watcher_prey = new EntityWatcher(prey);
                                    watcher_prey.setPred(null);
                                    prey.teleport(pred);
                                })
                );
        watcher.setPreys(null);
    }

    /// Escapes the predator. If the prey's pred is itself inside another pred, then
    /// the escapee prey becomes the higher predator's prey.
    ///
    /// @param prey             Prey who escapes the predator
    public static void escape(@Nonnull Entity prey) {
        EntityWatcher watcher_prey = new EntityWatcher(prey);
        if (watcher_prey.getPred() == null)
            return;
        Entity pred = Bukkit.getEntity(Objects.requireNonNull(watcher_prey.getPred()));
        if (pred == null)
            watcher_prey.setPred(null);
        else {
            EntityWatcher watcher_pred = new EntityWatcher(pred);
            watcher_prey.setPred(watcher_pred.getPred());
            watcher_pred.removePrey(prey.getUniqueId());
            prey.teleport(pred);
        }
    }

    /// Escapes all predators, no matter how deep in a voreception the prey could be,
    /// ensuring its complete freedom.
    ///
    /// @param prey             Prey to escapes all predators
    public static void escape_all(@Nonnull Entity prey) {
        Entity apex_pred = getApexPredator(prey);
        if (apex_pred == null)
            return;
        EntityWatcher watcher = new EntityWatcher(prey);
        new EntityWatcher(
                Bukkit.getEntity(Objects.requireNonNull(watcher.getPred()))
        ).removePrey(prey.getUniqueId());
        watcher.setPred(null);
        prey.teleport(apex_pred);
    }

    /// Completely resets its status, and frees if engulfed, the target entity.
    /// Its own preys will also be released outside, or within its pred.
    ///
    /// @param entity           Player to reset
    public static void halt(@Nonnull Entity entity) {
        // Release preys then frees himself
        release(entity);
        escape_all(entity);

        // Reset the player's status
        EntityWatcher watcher = new EntityWatcher(entity);
        watcher.setPred(false);
        watcher.setPrey(false);
        watcher.setFree(false);
        watcher.setPred(null);
        watcher.setPreys(null);
    }
}
