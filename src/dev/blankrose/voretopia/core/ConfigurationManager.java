package dev.blankrose.voretopia.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.TreeMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

/// << Singleton >>
/// ConfigManager
///
/// Singleton class which handles all the plugin's configurations.
/// It also allows for chaining when setting up.
public class ConfigurationManager {
    private static class ConfigurationEntry {
        public String resource; // Path of original base resource
        public String target; // Path of user configuration file
        public YamlConfiguration config; // Configuration object

        public ConfigurationEntry(String resource, String target, YamlConfiguration config) {
            this.resource = resource;
            this.target = target;
            this.config = config;
        }
    }

    private final Map<String, ConfigurationEntry> configs;
    private final File data_folder;
    private final Logger logs;

    private static ConfigurationManager instance = null;
    private ConfigurationManager() {
        configs = new TreeMap<>();
        data_folder = Provider.getPlugin().getDataFolder();
        logs = Provider.getPlugin().getLogger();
    }

    public static ConfigurationManager getInstance() {
        if (instance == null)
            instance = new ConfigurationManager();
        return instance;
    }

    // Internal Methods
    //////////////////////////////

    private boolean saveTarget(String resource, String target, YamlConfiguration config) {
        if (resource == null || target == null)
            throw new NullPointerException("Resource and target cannot be null!");

        // Creates data folder is non-existent
        if (!data_folder.exists())
            data_folder.mkdir();

        // Save default config file (or create if missing)
        File file = new File(data_folder, target);
        if (!file.exists()) {
            try {
                InputStream src = Provider.getPlugin().getResource(resource);
                OutputStream dst = new FileOutputStream(file);

                byte[] buf = new byte[2048];
                int len;
                while ((len = src.read(buf)) > 0)
                    dst.write(buf, 0, len);

                src.close();
                dst.close();
            } catch (IOException error) {
                Provider.getPlugin().getLogger().severe("Failed to retrieve default configuration file for " + target + "!");
                error.printStackTrace();
                return false;
            }
            loadTarget(resource, target); // Ensures config copied over WITH comments
        } else {
            try {
                if (config == null)
                    return false;
                config.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private YamlConfiguration loadTarget(String resource, String target) {
        if (resource == null || target == null)
            throw new NullPointerException("Resource and target cannot be null!");

        // Loads configuration file or creates if missing
        File file = new File(data_folder, target);
        if (!data_folder.exists() || !file.exists())
            if (!saveTarget(resource, target, null))
                return null;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        // Appends defaults and save (in case of missing)
        config.setDefaults(YamlConfiguration.loadConfiguration(
                new InputStreamReader(Provider.getPlugin().getResource(resource)))
        );
        config.options().copyDefaults(true);

        // Re-save configuration file (in case of missing defaults)
        saveTarget(resource, target, config);
        return config;
    }

    // Methods
    //////////////////////////////

    public ConfigurationManager addConfig(String id, String target) {
        return addConfig(id, target, target);
    }

    public ConfigurationManager addConfig(String id, String resource, String target) {
        if (id == null || resource == null || target == null)
            throw new NullPointerException("Resource and target cannot be null!");
        configs.put(id, new ConfigurationEntry(resource, target, null));
        return this;
    }

    public ConfigurationManager removeConfig(String id) {
        configs.remove(id);
        return this;
    }

    public ConfigurationManager loadConfigs() {
        int fails = 0;
        for (ConfigurationEntry entry : configs.values()) {
            entry.config = loadTarget(entry.resource, entry.target);
            if (entry.config == null)
                fails++;
        }

        logs.info("Successfully loaded all configurations!");
        if (fails > 0)
            logs.warning("Failed to load " + fails + " configuration(s)!");
        return this;
    }

    public ConfigurationManager saveConfigs() {
        int fails = 0;
        for (ConfigurationEntry entry : configs.values())
            if (!saveTarget(entry.resource, entry.target, entry.config))
                fails++;

        logs.info("Successfully saved all configurations!");
        if (fails > 0)
            logs.warning("Failed to save " + fails + " configuration(s)!");
        return this;
    }

    // Getters & Setters
    //////////////////////////////

    public Configuration getDefaultConfig(String id) {
        if (id == null)
            throw new NullPointerException("ID cannot be null!");
        if (!configs.containsKey(id))
            return null;
        return configs.get(id).config.getDefaults();
    }

    public Object getDefaultConfig(String id, String path) {
        if (id == null || path == null)
            throw new NullPointerException("Path cannot be null!");
        if (!configs.containsKey(id))
            return null;
        return configs.get(id).config.getDefaults().get(path);
    }

    public Configuration getConfig(String id) {
        if (id == null)
            throw new NullPointerException("ID cannot be null!");
        if (!configs.containsKey(id))
            return null;
        return configs.get(id).config;
    }

    public Object getConfig(String id, String path) {
        if (id == null || path == null)
            throw new NullPointerException("Path cannot be null!");
        if (!configs.containsKey(id))
            return null;
        return configs.get(id).config.get(path);
    }

    // Safe version of getConfig which checks for the given type
    // and returns the default value if the value is not of the given type
    public <T> T getConfig(String id, String path, Class<T> type) {
        if (id == null || path == null || type == null)
            throw new NullPointerException("Path and type cannot be null!");
        if (!configs.containsKey(id))
            return null;

        Object value = configs.get(id).config.get(path);
        if (!type.isInstance(value))
            return type.cast(getDefaultConfig(id, path));
        return type.cast(value);
    }

    public void setConfig(String id, Configuration config) {
        if (id == null || config == null)
            throw new NullPointerException("Configuration cannot be null!");
        if (!configs.containsKey(id))
            throw new NullPointerException("Configuration does not exist!");
        configs.get(id).config = (YamlConfiguration) config;
    }

    public void setConfig(String id, String path, Object value) {
        if (id == null || path == null || value == null)
            throw new NullPointerException("Path and value cannot be null!");
        if (!configs.containsKey(id))
            throw new NullPointerException("Configuration does not exist!");
        configs.get(id).config.set(path, value);
    }
}
