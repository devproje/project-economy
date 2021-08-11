package net.projecttl.pbalance.api

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class Economy(private val player: Player) {

    private val statement = InitSQLDriver.sqlConnection.createStatement()

    var money: Int
        get() = queryMoney()

        set(amount) {
            statement.executeUpdate("update ${InitSQLDriver.database}.${InitSQLDriver.table} " +
                    "set ${InitSQLDriver.balance} = $amount " +
                    "where ${InitSQLDriver.uuid} = '${player.uniqueId}' or ${InitSQLDriver.username} = '${player.name}';")
        }

    fun addMoney(amount: Int) {
        money += amount
    }

    fun removeMoney(amount: Int) {
        money -= amount
    }

    fun getRanking() {
        val resultSets = statement.executeQuery("SELECT * FROM ${InitSQLDriver.database}.${InitSQLDriver.table} " +
                "ORDER BY length(${InitSQLDriver.balance}) desc, ${InitSQLDriver.balance} desc;")
        var i = 1
        while (resultSets.next()) {
            when (i) {
                1 -> {
                    player.sendMessage("${i}st: ${resultSets.getString(InitSQLDriver.username)} => ${resultSets.getInt(InitSQLDriver.balance)}${moneyUnit()}")
                }

                2 -> {
                    player.sendMessage("${i}nd: ${resultSets.getString(InitSQLDriver.username)} => ${resultSets.getInt(InitSQLDriver.balance)}${moneyUnit()}")
                }

                3 -> {
                    player.sendMessage("${i}rd: ${resultSets.getString(InitSQLDriver.username)} => ${resultSets.getInt(InitSQLDriver.balance)}${moneyUnit()}")
                }

                else -> {
                    player.sendMessage("${i}th: ${resultSets.getString(InitSQLDriver.username)} => ${resultSets.getInt(InitSQLDriver.balance)}${moneyUnit()}")
                }
            }

            i++
        }
    }

    fun queryList() {
        val resultSets = statement.executeQuery("select * from ${InitSQLDriver.database}.${InitSQLDriver.table};")
        while (resultSets.next()) {
            player.sendMessage("${ChatColor.GOLD}===============================================\n" +
                    "${ChatColor.GREEN}UUID: ${ChatColor.RESET}${resultSets.getString(InitSQLDriver.uuid)}\n" +
                    "${ChatColor.GREEN}NAME: ${ChatColor.RESET}${resultSets.getString(InitSQLDriver.username)}\n" +
                    "${ChatColor.GREEN}BALANCE: ${ChatColor.RESET}${resultSets.getInt(InitSQLDriver.balance)}${moneyUnit()}\n" +
                    "${ChatColor.GOLD}===============================================")
        }
    }

    private fun queryMoney(): Int {
        val resultSet = statement.executeQuery("select * from ${InitSQLDriver.database}.${InitSQLDriver.table}" +
                " where ${InitSQLDriver.username} = '${player.name}' and ${InitSQLDriver.uuid} = '${player.uniqueId}'")
        var result: Int? = null

        if (resultSet.next()) {
            result = resultSet.getInt(InitSQLDriver.balance)
        }

        return result!!
    }

    fun registerAccount() {
        statement.executeUpdate("insert ignore into ${InitSQLDriver.database}.${InitSQLDriver.table} values('${player.uniqueId}', '${player.name}', 0);")
    }
}