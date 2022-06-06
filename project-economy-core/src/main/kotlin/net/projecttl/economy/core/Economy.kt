package net.projecttl.economy.core

import net.projecttl.economy.core.utils.moneyUnit
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

class Economy(val player: Player) {

    var money: Int
        get() {
            val stmt = conn.prepareStatement("select amount from account where uuid = ?;")
            stmt.setString(1, player.uniqueId.toString())

            val res = stmt.executeQuery()
            while (res.next()) {
                return res.getInt("amount")
            }

            return 0
        }
        set(value) {
            val stmt = conn.prepareStatement("update account set amount = ? where uuid = ?;")
            stmt.setInt(1, value)
            stmt.setString(2, player.uniqueId.toString())

            stmt.executeUpdate()
        }

    fun addMoney(amount: Int): Boolean {
        if (amount < 0) return false
        money += amount
        return true
    }

    fun dropMoney(amount: Int): Boolean {
        if (amount < 0) return false
        if (money < amount) return false
        money -= amount
        return true
    }

    fun getRanking() {
        val stmt = conn.prepareStatement("select * from account order by length(amount) desc, amount desc;")
        val resultSets = stmt.executeQuery()
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

            if (i % 5 == 0) {
                return
            } else {
                i++
            }
        }
    }

    private fun getAccounts(): MutableMap<UUID, Int> {
        val stmt = conn.prepareStatement("select * from account;")
        val resultSets = stmt.executeQuery()

        val list = mutableMapOf<UUID, Int>()
        while (resultSets.next()) {
            list += UUID.fromString(resultSets.getString("uuid")) to resultSets.getInt("amount")
        }

        return list
    }

    fun addAccount() {
        val stmt = conn.prepareStatement("insert ignore into account values(?, 0)")
        stmt.setString(1, player.uniqueId.toString())

        stmt.executeUpdate()
    }

    fun showAccount(): Boolean {
        for (i in getAccounts()) {
            if (i.key == player.uniqueId) {
                player.sendMessage(
                        "${ChatColor.GOLD}==========[${ChatColor.RESET}${Bukkit.getPlayer(i.key)?.name}'s account${ChatColor.GOLD}]=========="
                )
                player.sendMessage("${ChatColor.YELLOW}UUID${ChatColor.RESET}: ${i.key}")
                player.sendMessage("${ChatColor.YELLOW}Balance${ChatColor.RESET}: ${i.value}${moneyUnit}")

                return true
            }
        }

        return false
    }
}