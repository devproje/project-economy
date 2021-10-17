package net.projecttl.economy.plugin;

import net.projecttl.economy.InitSQL;
import net.projecttl.economy.plugin.commands.EconomyCommand;
import net.projecttl.economy.plugin.listeners.RegisterListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public class ProjectEconomy extends JavaPlugin {

    private final Logger logger = this.getLogger();
    private final InitSQL database = new InitSQL(this);

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            saveDefaultConfig();
        }

        logger.info(ChatColor.GREEN + "Project Economy plugin has enabled!");

        Objects.requireNonNull(getCommand("money")).setExecutor(new EconomyCommand(this));
        getServer().getPluginManager().registerEvents(new RegisterListener(this), this);
    }

    @Override
    public void onDisable() {
        logger.info(ChatColor.RED + "Project Economy plugin has disabled!");
        saveConfig();
    }

    public void connect() {
        database.connect();
    }

    public void disconnect() {
        database.disconnect();
    }
}