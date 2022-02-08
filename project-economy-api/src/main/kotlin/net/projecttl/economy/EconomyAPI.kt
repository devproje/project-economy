package net.projecttl.economy

import net.projecttl.economy.plugin.utils.*
import net.projecttl.economy.plugin.utils.moneyUnit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

private val moneyUnit: String = InitSQL.moneyUnits.toString()

fun moneyUnit(): String {
    return if (moneyUnit.length > 3) {
        " $moneyUnit"
    } else {
        moneyUnit
    }
}

fun economyOf(player: Player, plugin: JavaPlugin): EconomyAPI {
    return EconomyAPI(player, plugin)
}

class EconomyAPI(player: Player, plugin: JavaPlugin) {

    private val economy = Economy(player)

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
            return moneyUnit()
        }

        set(new_unit) {
            setMoneyUnit(new_unit)
        }

    fun addMoney(amount: Int) {
        economy.addMoney(amount)
    }

    fun subtractMoney(amount: Int) {
        economy.subtractMoney(amount)
    }
}