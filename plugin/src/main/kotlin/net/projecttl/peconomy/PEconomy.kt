package net.projecttl.peconomy

import net.projecttl.peconomy.commands.MoneyCommand
import net.projecttl.peconomy.listeners.RegisterListener
import net.projecttl.peconomy.api.InitSQLDriver
import org.bukkit.plugin.java.JavaPlugin

class PEconomy : JavaPlugin() {

    override fun onEnable() {
        if (!dataFolder.exists()) {
            this.saveDefaultConfig()
        }

        InitSQLDriver(this).loadSQLModule()
        getCommand("peconomy")?.apply {
            setExecutor(MoneyCommand(this@PEconomy))
            tabCompleter = MoneyCommand(this@PEconomy)
        }

        getCommand("exchange")?.apply {
            setExecutor(MoneyCommand(this@PEconomy))
        }

        server.pluginManager.apply {
            registerEvents(RegisterListener(), this@PEconomy)
        }
    }

    override fun onDisable() {
        InitSQLDriver(this).closeConnection()
        saveConfig()
    }
}