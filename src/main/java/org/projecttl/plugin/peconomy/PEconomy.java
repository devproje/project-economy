package org.projecttl.plugin.peconomy;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.projecttl.plugin.peconomy.commands.MoneyCommand;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PEconomy extends JavaPlugin {

    private File config;
    private FileConfiguration configuration;

    @Override
    public void onEnable() {
        load();

        Objects.requireNonNull(getCommand("peconomy")).setExecutor(new MoneyCommand(this));
        Objects.requireNonNull(getCommand("peconomy")).setTabCompleter(new MoneyCommand(this));
    }

    @Override
    public void onDisable() {
        save();
    }

    private void load() {
        config = new File(getDataFolder(), "config.yml");
        configuration = YamlConfiguration.loadConfiguration(config);

        try {
            if (!config.exists()) {
                configuration.save(config);
            }

            configuration.load(config);
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    private void save() {
        try {
            configuration.save(config);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public FileConfiguration pEconomyConfig() {
        return configuration;
    }
}
