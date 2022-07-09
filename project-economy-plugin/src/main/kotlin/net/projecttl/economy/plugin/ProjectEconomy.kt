package net.projecttl.economy.plugin

import io.github.monun.kommand.kommand
import net.projecttl.economy.core.Database
import net.projecttl.economy.core.model.DatabaseCredential
import net.projecttl.economy.plugin.listeners.RegisterListener
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

lateinit var instance: ProjectEconomy
lateinit var database: Database

class ProjectEconomy : JavaPlugin() {

    override fun onEnable() {
        instance = this
        net.projecttl.economy.core.instance = this

        if (!dataFolder.exists()) {
            this.saveDefaultConfig()
        }


        val model: DatabaseCredential = try {
            DatabaseCredential(
                config.getString("DB_URL")!!,
                config.getInt("DB_PORT"),
                config.getString("DB_NAME")!!,
                config.getString("DB_USERNAME")!!,
                config.getString("DB_PASSWORD")!!
            )
        } catch (exception: Exception) {
            logger.info("${ChatColor.RED}Please type database credentials!")
            return
        }

        database = Database(model)
        database.connect()

        getCommand("money")?.apply {
            setExecutor(CommandDispatcher)
            tabCompleter = CommandDispatcher
        }

        getCommand("send")?.apply {
            setExecutor(CommandDispatcher)
            tabCompleter = CommandDispatcher
        }

        getCommand("settings")?.apply {
            setExecutor(CommandDispatcher)
            tabCompleter = CommandDispatcher
        }

        kommand {
            CommandDispatcher.register(this)
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