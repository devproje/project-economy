package net.projecttl.economy

import net.projecttl.economy.plugin.utils.Economy
import net.projecttl.economy.plugin.utils.InitSQL
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

private val moneyUnit: String = InitSQL.moneyUnits.toString()

fun moneyUnit(): String {
    return if (moneyUnit.length > 3) {
        " $moneyUnit"
    } else {
        moneyUnit
    }
}

fun economyOf(player: Player): EconomyAPI {
    return EconomyAPI(player)
}

class EconomyAPI(player: Player) {

    private val economy = Economy(player)

    var money: Int
    get() {
        return economy.money
    }
    set(amount) {
        economy.money = amount
    }

    fun addMoney(amount: Int) {
        economy.addMoney(amount)
    }

    fun removeMoney(amount: Int) {
        economy.removeMoney(amount)
    }

    fun buy(item: ItemStack, amount: Int) {
        economy.buy(item, amount)
    }

    fun sell(item: ItemStack, amount: Int) {
        economy.sell(item, amount)
    }

    fun buySet(item: ItemStack) {
        economy.buySet(item)
    }

    fun sellSet(item: ItemStack) {
        economy.sellSet(item)
    }

    fun sellAll(item: ItemStack) {
        economy.sellAll(item)
    }
}