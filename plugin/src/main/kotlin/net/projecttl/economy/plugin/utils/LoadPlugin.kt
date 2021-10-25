package net.projecttl.economy.plugin.utils

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

private val logger: Logger = Bukkit.getServer().logger

fun loadPlugin(plugin: JavaPlugin) {
    if (Bukkit.getServer().pluginManager.getPlugin("project-economy") != null) {
        logger.info("project-economy API has successful loaded!")
    } else {
        logger.info("${ChatColor.RED}This API must required project-economy plugin!")
        Bukkit.getServer().pluginManager.disablePlugin(plugin)
    }
}