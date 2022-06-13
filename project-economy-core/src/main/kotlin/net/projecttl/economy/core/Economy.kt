package net.projecttl.economy.core

import net.projecttl.economy.core.utils.moneyUnit
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
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
        if (amount <= 0) return false
        money += amount
        return true
    }

    fun dropMoney(amount: Int): Boolean {
        if (amount <= 0) return false
        if (money < amount) return false
        money -= amount
        return true
    }

    fun addAccount() {
        val stmt = conn.prepareStatement("insert ignore into account values(?, 0)")
        stmt.setString(1, player.uniqueId.toString())

        stmt.executeUpdate()
    }

    fun showAccount(viewer: CommandSender): Boolean {
        for (i in getAccounts()) {
            if (i.key == player.uniqueId) {
                viewer.sendMessage("""
                    ${ChatColor.GOLD}==========[${ChatColor.RESET}${Bukkit.getPlayer(i.key)?.name}'s account${ChatColor.GOLD}]==========
                    ${ChatColor.YELLOW}UUID${ChatColor.RESET}: ${i.key}
                    ${ChatColor.YELLOW}Balance${ChatColor.RESET}: ${i.value}${moneyUnit}
                """.trimIndent())
                return true
            }
        }

        return false
    }

    companion object {
        private fun getAccounts(): MutableMap<UUID, Int> {
            val stmt = conn.prepareStatement("select * from account;")
            val resultSets = stmt.executeQuery()

            val list = mutableMapOf<UUID, Int>()
            while (resultSets.next()) {
                list += UUID.fromString(resultSets.getString("uuid")) to resultSets.getInt("amount")
            }

            return list
        }

        fun CommandSender.queryAccount() {
            for (i in getAccounts()) {
                this.sendMessage("""
                    ${ChatColor.GOLD}=============================================
                    ${ChatColor.YELLOW}NAME: ${ChatColor.WHITE}${Bukkit.getOfflinePlayer(i.key).name}
                    ${ChatColor.YELLOW}UUID: ${ChatColor.WHITE}${i.key}
                    ${ChatColor.YELLOW}AMOUNT: ${ChatColor.WHITE}${i.value}${moneyUnit}
                    ${ChatColor.GOLD}=============================================
                """.trimIndent())
            }
        }
    }
}