package net.projecttl.pbalance.api

import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class Economy(private val player: Player) {

    private val statement = InitSQLDriver.sqlConnection.createStatement()

    var money: Int
        get() = queryMoney()

        set(amount) {
            statement.executeUpdate("update PBalance.account set amount = $amount where username = '${player.name}' or uuid = '${player.uniqueId}';")
        }

    fun addMoney(amount: Int) {
        money += amount
    }

    fun removeMoney(amount: Int) {
        money -= amount
    }

    fun queryList() {
        val resultSets = statement.executeQuery("select * from account;")
        while (resultSets.next()) {
            player.sendMessage("${ChatColor.GOLD}===============================================\n" +
                    "${ChatColor.GREEN}ID: ${ChatColor.RESET}${resultSets.getInt(1)}\n" +
                    "${ChatColor.GREEN}NAME: ${ChatColor.RESET}${resultSets.getString(2)}\n" +
                    "${ChatColor.GREEN}UUID: ${ChatColor.RESET}${resultSets.getString(3)}\n" +
                    "${ChatColor.GREEN}BALANCE: ${ChatColor.RESET}${resultSets.getInt(4)}₩\n" +
                    "${ChatColor.GOLD}===============================================")
        }
    }

    fun sellWithItem(item: ItemStack, amount: Int) {
        if (!player.inventory.containsAtLeast(item, item.amount)) {
            player.sendMessage("<PEconomy> ${ChatColor.RED}Not enough ${item.type.name.lowercase()} in your inventory")
        } else {
            player.playSound(player.location, Sound.ENTITY_ARROW_HIT_PLAYER, 1F, 0F)
            player.inventory.removeItem(item)
            addMoney(amount)
        }
    }

    fun exchangeWithItem(item: ItemStack, amount: Int) {
        if (amount > money) {
            player.sendMessage("<PEconomy> ${ChatColor.RED}Not enough balance in your account")
        } else {
            player.playSound(player.location, Sound.ENTITY_ARROW_HIT_PLAYER, 1F, 1F)
            removeMoney(amount)
            player.inventory.addItem(item)
        }
    }

    private fun queryMoney(): Int {
        val resultSet = statement.executeQuery("select amount from PBalance.account where username = '${player.name}' and uuid = '${player.uniqueId}'")
        var result: Int? = null

        if (resultSet.next()) {
            result = resultSet.getInt(1)
        }

        return result!!
    }

    fun registerAccount() {
        statement.executeUpdate("insert into PBalance.account values(NULL, '${player.name}', '${player.uniqueId}', 0);")
    }
}