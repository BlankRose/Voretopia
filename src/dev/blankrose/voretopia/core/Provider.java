package dev.blankrose.voretopia.core;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.logging.Logger;

public class Provider {
    private static JavaPlugin plugin;

    public static void init(@Nonnull JavaPlugin plugin) {
        if (Provider.plugin != null)
            throw new IllegalStateException("Provider has been already initialized");
        Provider.plugin = plugin;
    }

    @Nonnull
    public static JavaPlugin getPlugin() {
        return plugin;
    }

    @Nonnull
    public static NamespacedKey getKey(String name) {
        return new NamespacedKey(plugin, name);
    }

    @Nonnull
    public static Collection<? extends Player> getOnlinePlayers() {
        return plugin.getServer().getOnlinePlayers();
    }

    @Nonnull
    public static Logger getLogger() {
        return plugin.getLogger();
    }

    public static void notifyPlayer(@Nonnull Player player, @Nonnull String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }
}
