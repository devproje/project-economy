package net.projecttl.economy.plugin.utils

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

class Economy(val player: Player) {

    private val statement = InitSQL.connection.createStatement()

    var money: Int
        get() {
            val res = statement.executeQuery("select amount from account where uuid = '${player.uniqueId}';")
            while (res.next()) {
                return res.getInt("amount")
            }
            return 0
        }
        set(value) {
            statement.executeUpdate(
                "update account set amount = $value where uuid = '${player.uniqueId}';"
            )
        }

    fun addMoney(amount: Int): Boolean {
        if (amount <= 0) return false
        money += amount
        return true
    }

    fun payMoney(amount: Int): Boolean {
        if (amount < 0) return false
        if (money < amount) return false
        money -= amount
        return true
    }

    fun subtractMoney(amount: Int): Boolean {
        if (amount <= 0) return false
        if (money < amount) money = 0
        else money -= amount
        return true
    }

    fun getRanking() {
        val resultSets = statement.executeQuery(
            "SELECT * FROM account ORDER BY length(amount) desc, amount desc;"
        )
        var i = 1
        while (resultSets.next()) {
            val username = Bukkit.getPlayer(resultSets.getString("uuid"))?.name
            val amount = resultSets.getInt("amount")

            when (i) {
                1 -> {
                    player.sendMessage("${ChatColor.GOLD}$i: ${ChatColor.WHITE}$username => $amount${moneyUnit}")
                }

                2 -> {
                    player.sendMessage("${ChatColor.GRAY}${i}: ${ChatColor.WHITE}$username => $amount${moneyUnit}")
                }

                3 -> {
                    player.sendMessage("${ChatColor.YELLOW}${i}: ${ChatColor.WHITE}$username => $amount${moneyUnit}")
                }

                else -> {
                    player.sendMessage("${ChatColor.GRAY}${i}: ${ChatColor.WHITE}$username => $amount${moneyUnit}")
                }
            }

            i++
        }
    }

    fun registerAccount() {
        statement.executeUpdate("insert ignore into account values('${player.uniqueId}', 0);")
    }

    fun showAccount() {
        for (i in queryAccount().values) {
            if (i.uuid == player.uniqueId) {
                player.sendMessage(
                    "${ChatColor.GOLD}==========[${ChatColor.RESET}${Bukkit.getPlayer(i.uuid)?.name}'s account${ChatColor.GOLD}]=========="
                )
                player.sendMessage("${ChatColor.YELLOW}UUID${ChatColor.RESET}: ${i.uuid}")
                player.sendMessage("${ChatColor.YELLOW}Balance${ChatColor.RESET}: ${i.amount}${moneyUnit}")
            }
        }
    }

    fun queryList() {
        for (i in queryAccount().values) {
            player.sendMessage(
                """
                ${ChatColor.GOLD}===============================================
                ${ChatColor.GREEN}UUID: ${ChatColor.RESET}${i.uuid}
                ${ChatColor.GREEN}NAME: ${ChatColor.RESET}${Bukkit.getPlayer(i.uuid)?.name}
                ${ChatColor.GREEN}BALANCE: ${ChatColor.RESET}${i.amount}${moneyUnit}
                ${ChatColor.GOLD}===============================================
                """.trimIndent()
            )
        }
    }

    private fun queryAccount(): MutableMap<UUID, Account> {
        val resultSets = statement.executeQuery("SELECT * FROM account;")
        val list = mutableMapOf<UUID, Account>()
        while (resultSets.next()) {
            list += UUID.fromString(resultSets.getString("uuid")) to Account(
                UUID.fromString(resultSets.getString("uuid")),
                resultSets.getInt("amount")
            )
        }

        return list
    }
}