package net.projecttl.pbalance

import net.projecttl.pbalance.commands.MoneyCommand
import net.projecttl.pbalance.listeners.RegisterListener
import net.projecttl.pbalance.api.InitSQLDriver
import org.bukkit.plugin.java.JavaPlugin

class PBalance : JavaPlugin() {

    override fun onEnable() {
        if (!dataFolder.exists()) {
            this.saveDefaultConfig()
        }

        InitSQLDriver(this).loadSQLModule()
        getCommand("pbalance")?.apply {
            setExecutor(MoneyCommand(this@PBalance))
            tabCompleter = MoneyCommand(this@PBalance)
        }

        server.pluginManager.apply {
            registerEvents(RegisterListener(), this@PBalance)
        }
    }

    override fun onDisable() {
        InitSQLDriver(this).closeConnection()
        saveConfig()
    }
}