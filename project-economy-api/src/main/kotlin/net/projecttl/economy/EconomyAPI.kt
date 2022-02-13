package net.projecttl.economy

import net.projecttl.economy.plugin.utils.*
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
private val logger: Logger = Bukkit.getServer().logger

private fun loadPlugin(plugin: JavaPlugin) {
    if (Bukkit.getServer().pluginManager.getPlugin("project-economy") == null) {
        logger.info("${ChatColor.RED}This API must required project-economy plugin!")
        Bukkit.getServer().pluginManager.disablePlugin(plugin)
        return
    }

    logger.info("project-economy API has successful loaded!")
}

class EconomyAPI(player: Player, plugin: JavaPlugin) {

    val economy = Economy(player)

    init {
        loadPlugin(plugin)
    }

    var money: Int
        get() {
            return economy.money
        }
        set(amount) {
            economy.money = amount
        }

    var moneyUnit: String
        get() {
            return moneyUnit
        }

        set(new_unit) {
            moneyUnit = new_unit
        }

    fun addMoney(amount: Int) {
        economy.addMoney(amount)
    }

    fun subtractMoney(amount: Int) {
        economy.subtractMoney(amount)
    }
}