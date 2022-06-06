package net.projecttl.economy

import net.projecttl.economy.core.Economy
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

private fun JavaPlugin.load() {
    if (Bukkit.getServer().pluginManager.getPlugin("project-economy") == null) {
        logger.info("${ChatColor.RED}This API must required project-economy plugin!")
        Bukkit.getServer().pluginManager.disablePlugin(this)
        return
    }
}

class EconomyAPI(val plugin: JavaPlugin, val player: Player) {

    val economy = Economy(player)

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

    fun dropMoney(amount: Int) {
        economy.dropMoney(amount)
    }

    init {
        plugin.load()
    }
}