package net.projecttl.peconomy

import net.projecttl.peconomy.commands.MoneyCommand
import net.projecttl.peconomy.utils.InitSQLDriver
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
    }

    override fun onDisable() {
        InitSQLDriver(this).closeConnection()
        saveConfig()
    }
}