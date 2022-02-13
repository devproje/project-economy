package net.projecttl.economy.plugin

import net.projecttl.economy.plugin.listeners.RegisterListener
import net.projecttl.economy.plugin.utils.InitSQL
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

lateinit var instance: ProjectEconomy

class ProjectEconomy : JavaPlugin() {

    private val database = InitSQL(this)

    override fun onEnable() {
        instance = this

        if (!dataFolder.exists()) {
            this.saveDefaultConfig()
        }

        database.connect()

        getCommand("money")?.apply {
            setExecutor(CommandDispatcher)
            tabCompleter = CommandDispatcher
        }

        server.pluginManager.registerEvents(RegisterListener(), this)
        logger.info("${ChatColor.GREEN}Project Economy plugin has enabled!")
    }

    override fun onDisable() {
        logger.info("${ChatColor.RED}Project Economy plugin has disabled!")
        saveConfig()
        database.disconnect()
    }
}