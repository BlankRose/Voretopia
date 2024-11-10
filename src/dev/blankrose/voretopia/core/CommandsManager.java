package dev.blankrose.voretopia.core;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import dev.blankrose.voretopia.commands.CancelCompletion;
import dev.blankrose.voretopia.commands.ConfigCheckerCommand;
import dev.blankrose.voretopia.commands.ListCommand;
import dev.blankrose.voretopia.commands.ReloadCommand;
import dev.blankrose.voretopia.commands.VoreCommand;
import dev.blankrose.voretopia.commands.VoreCompletion;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/// << Static >>
/// CommandsManager
///
/// Registers and handles commands of the plugin.
public class CommandsManager {
    private CommandsManager() {}

    /// Registers a single command for the plugin.
    ///
    /// @param name             Name of the command to register
    /// @param exec             What to execute upon call
    /// @param completer        How to complete upon typing
    ///
    /// @implNote               If command is not registered within `plugin.yml`, it will be ignored
    private static void registerSingle(@Nonnull String name, @Nonnull CommandExecutor exec, @Nonnull TabCompleter completer) {
        PluginCommand cmd = getCommand(name);
        if (cmd == null)
            return;
        cmd.setExecutor(exec);
        cmd.setTabCompleter(completer);
    }

    /// Registers all commands of the plugin.
    public static void registerCommands() {
        registerSingle("vore-reload",
                new ReloadCommand(),
                new CancelCompletion());
        registerSingle("vore",
                new VoreCommand(),
                new VoreCompletion());
        registerSingle("vore-list",
                new ListCommand(),
                new CancelCompletion());
        registerSingle("config-checker",
                new ConfigCheckerCommand(),
                new CancelCompletion());

        Provider.getLogger().info("All commands has been successfully registered!");
    }

    /// Retrieves a command from this plugin
    ///
    /// @param name             Name of the command
    /// @return                 The command, otherwise `null` if it wasn't found
    ///                         (likely it wasn't saved in the `plugin.yml` file)
    @Nullable
    public static PluginCommand getCommand(String name) {
        return Provider.getPlugin().getCommand(name);
    }
}
