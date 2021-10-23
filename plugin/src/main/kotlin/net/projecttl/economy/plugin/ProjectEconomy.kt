package net.projecttl.economy.plugin

import net.projecttl.economy.plugin.commands.EconomyCommand
import net.projecttl.economy.plugin.commands.ExchangeCommand
import net.projecttl.economy.plugin.listeners.RegisterListener
import net.projecttl.economy.plugin.utils.InitSQL
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

lateinit var instance: ProjectEconomy

class ProjectEconomy : JavaPlugin() {

    private val database = InitSQL(this)

    override fun onEnable() {
        instance = this

        if (!dataFolder.exists()) {
            saveDefaultConfig()
        }

        connect()
        logger.info("${ChatColor.GREEN}Project Economy plugin has enabled!")
        getCommand("money")?.apply {
            setExecutor(EconomyCommand)
            tabCompleter = EconomyCommand
        }

        getCommand("exchanger")?.setExecutor(ExchangeCommand)
        server.pluginManager.registerEvents(RegisterListener(), this)
    }

    override fun onDisable() {
        logger.info("${ChatColor.RED}Project Economy plugin has disabled!")
        saveConfig()
        disconnect()
    }

    fun connect() {
        database.connect()
    }

    fun disconnect() {
        database.disconnect()
    }
}